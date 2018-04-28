package org.ljl.look.api.service;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.dto.TopicWithAudit;
import org.ljl.look.api.entity.Message;
import org.ljl.look.api.entity.Topic;
import org.ljl.look.api.entity.TopicAudit;
import org.ljl.look.api.entity.TopicFocus;
import org.ljl.look.api.feign.ActivityServiceFeign;
import org.ljl.look.api.feign.AuditServiceFeign;
import org.ljl.look.api.message.sender.MessageSender;
import org.ljl.look.api.message.sender.TopicAuditSender;
import org.ljl.look.api.message.sender.TopicFocusSender;
import org.ljl.look.api.message.sender.TopicSender;
import org.ljl.look.api.util.ReflectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicWithAuditService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuditServiceFeign auditServiceFeign;
    @Autowired
    private ActivityServiceFeign activityServiceFeign;

    @Autowired
    private TopicSender topicSender;
    @Autowired
    private TopicAuditSender topicAuditSender;
    @Autowired
    private TopicFocusSender topicFocusSender;
    @Autowired
    private MessageSender messageSender;

    public TopicWithAudit get(String uuid) {
        TopicWithAudit topicWithAudit = new TopicWithAudit();
        // 设置audit相关数据
        TopicAudit topicAudit = auditServiceFeign.getTopicAuditByTopicUuid(uuid);
        ReflectTool.copyCommonPropertyValue(topicWithAudit, topicAudit);
        topicWithAudit.setAuditUuid(topicAudit.getUuid());
        // 设置topic相关数据
        ReflectTool.copyCommonPropertyValue(
                topicWithAudit,
                activityServiceFeign.getTopic(uuid)
        );
        topicWithAudit.setTopicUuid(uuid);
        return topicWithAudit;
    }

    public List<TopicWithAudit> getsUserTopicWithAuditByState(String token, String state, String pageInfoJsonStr) {
        if (state.equals(ConstConfig.WAITING_AUDITED)) {
            return auditServiceFeign.getsTopicAuditUserAudited(token, pageInfoJsonStr).stream()
                    .map(topicAudit -> this.get(topicAudit.getTopicUuid()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public void update(String token, TopicWithAudit topicWithAudit) {
        // 更新 TopicAudit
        topicAuditSender.sendToUpdate(
                TopicAudit.builder()
                        .uuid(topicWithAudit.getAuditUuid())
                        .suggestion(topicWithAudit.getSuggestion())
                        .state(topicWithAudit.getState())
                        .auditUser(stringRedisTemplate.opsForValue().get(token))
                        .build()
        );
        // 审核通过
        if (topicWithAudit.getState() == ConstConfig.PASS_AUDIT_STATE) {
            // 更新 Topic
            topicSender.sendToUpdate(
                    Topic.builder()
                            .uuid(topicWithAudit.getTopicUuid())
                            .parentTopicUuid(topicWithAudit.getParentTopicUuid())
                            .valid(ConstConfig.VALID)
                            .build()
            );
            // 用户关注 topic
            topicFocusSender.sendToAdd(
                    TopicFocus.builder()
                            .fromUser(activityServiceFeign.getTopic(topicWithAudit.getTopicUuid()).getCreateUser())
                            .topicUuid(topicWithAudit.getTopicUuid())
                            .build()
            );
            // 给用户发送主题审核通过消息
            messageSender.sendToAdd(
                    Message.builder()
                            .toUser(token)
                            .title("审核通过：主题-" + topicWithAudit.getName())
                            .content(topicWithAudit.getTopicUuid())
                            .type(ConstConfig.AUDIT_MESSAGE).build()
            );
        } else { // 审核未通过
            // 给用户发送主题审核未通过消息
            messageSender.sendToAdd(
                    Message.builder()
                            .toUser(token)
                            .title("审核未通过：主题-" + topicWithAudit.getName())
                            .content(topicWithAudit.getTopicUuid())
                            .type(ConstConfig.AUDIT_MESSAGE).build()
            );
        }
    }
}
