package org.ljl.look.api.service;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.dto.FullActivity;
import org.ljl.look.api.dto.FullActivityWithAudit;
import org.ljl.look.api.entity.Activity;
import org.ljl.look.api.entity.ActivityAudit;
import org.ljl.look.api.entity.Join;
import org.ljl.look.api.entity.Message;
import org.ljl.look.api.feign.ActivityServiceFeign;
import org.ljl.look.api.feign.AuditServiceFeign;
import org.ljl.look.api.message.sender.ActivityAuditSender;
import org.ljl.look.api.message.sender.ActivitySender;
import org.ljl.look.api.message.sender.JoinSender;
import org.ljl.look.api.message.sender.MessageSender;
import org.ljl.look.api.util.ReflectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityWithAuditService {

    @Autowired
    private AuditServiceFeign auditServiceFeign;
    @Autowired
    private ActivityServiceFeign activityServiceFeign;
    @Autowired
    private FullActivityService fullActivityService;
    @Autowired
    private ActivityAuditSender activityAuditSender;
    @Autowired
    private ActivitySender activitySender;
    @Autowired
    private JoinSender joinSender;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public FullActivityWithAudit get(String uuid) {
        FullActivityWithAudit fullActivityWithAudit = new FullActivityWithAudit();
        // 设置audit相关数据
        ActivityAudit activityAudit = auditServiceFeign.getActivityAuditByActivityUuid(uuid);
        ReflectTool.copyCommonPropertyValue(fullActivityWithAudit, activityAudit);
        fullActivityWithAudit.setAuditUuid(activityAudit.getUuid());
        // 设置activity相关数据
        fullActivityWithAudit.setFullActivity(
                fullActivityService.getByUuid(uuid)
        );
        return fullActivityWithAudit;
    }

    public List<FullActivityWithAudit> getsByState(String token, String state, String pageInfoJsonStr) {
        if (state.equals(ConstConfig.WAITING_AUDITED)) {
            return auditServiceFeign.getsActivityAuditUserAudited(token, pageInfoJsonStr).stream()
                    .map(activityAudit -> this.get(activityAudit.getActivityUuid()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public void update(String token, FullActivityWithAudit activityWithAudit) {
        Activity activity = activityServiceFeign.getActivity(activityWithAudit.getFullActivity().getUuid());
        // 更新 ActivityAudit
        activityAuditSender.sendToUpdate(
                ActivityAudit.builder()
                        .uuid(activityWithAudit.getAuditUuid())
                        .suggestion(activityWithAudit.getSuggestion())
                        .state(activityWithAudit.getState())
                        .auditUser(stringRedisTemplate.opsForValue().get(token))
                        .build()
        );
        // 审核通过
        if (activityWithAudit.getState() == ConstConfig.PASS_AUDIT_STATE) {
            // 更新 Activity
            activitySender.sendToUpdate(
                    Activity.builder()
                            .uuid(activity.getUuid())
                            .valid(ConstConfig.VALID)
                            .build()
            );
            // 用户参与活动
            joinSender.sendToAdd(
                    Join.builder()
                            .fromUser(activity.getPublishUser())
                            .activityUuid(activity.getUuid())
                            .build()
            );
            // 给用户发送主题审核通过消息
            messageSender.sendToAdd(
                    Message.builder()
                            .toUser(token)
                            .title("通过：" + activity.getTitle() + "-活动")
                            .content(activity.getUuid())
                            .type(ConstConfig.AUDIT_MESSAGE).build()
            );
        } else { // 审核未通过
            // 给用户发送主题审核未通过消息
            messageSender.sendToAdd(
                    Message.builder()
                            .toUser(token)
                            .title("未通过：" + activity.getTitle() + "-活动")
                            .content(activity.getUuid())
                            .type(ConstConfig.AUDIT_MESSAGE).build()
            );
        }
    }
}
