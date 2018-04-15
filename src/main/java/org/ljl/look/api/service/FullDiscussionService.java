package org.ljl.look.api.service;

import org.ljl.look.api.dto.FullDiscussion;
import org.ljl.look.api.entity.Discussion;
import org.ljl.look.api.entity.User;
import org.ljl.look.api.feign.UserServiceFeign;
import org.ljl.look.api.util.ReflectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FullDiscussionService {

    @Autowired
    private UserServiceFeign userServiceFeign;

    public List<FullDiscussion> getsByBelongToActivity(String belongToActivity) {
        List<Discussion> discussions = userServiceFeign.getsByBelongToActivity(belongToActivity);
        return discussions.stream()
                .filter(discussion -> discussion.getBelongToDiscussion() == null) // 获取所有parent discussion
                // sorted by parent discussion publish date
                .sorted(Comparator.comparing(Discussion::getDiscussDate).reversed())
                .map(discussion -> {
                    /** 构造 discussion DTO */
                    FullDiscussion fullDiscussion = new FullDiscussion();
                    // 赋值共同属性
                    ReflectTool.copyCommonPropertyValue(fullDiscussion, discussion);
                    // 获取用户信息
                    User user = userServiceFeign.getUser(discussion.getFromUser());
                    fullDiscussion.setFromUserName(user.getName()); // 名称
                    fullDiscussion.setFromUserAvatar(user.getAvatar()); // 头像
                    // 获取child discussion
                    fullDiscussion.setFullSubDiscussions(
                            discussions.stream()
                                    // filter child discussion
                                    .filter(otherDiscussion -> otherDiscussion.getBelongToDiscussion().equals(discussion.getUuid()))
                                    // sorted by child discussion publish date
                                    .sorted(Comparator.comparing(Discussion::getDiscussDate).reversed())
                                    .map(subDiscussion ->
                                            FullDiscussion.FullSubDiscussion.builder()
                                                    .fromUser(subDiscussion.getFromUser())
                                                    .toUser(subDiscussion.getToUser())
                                                    .contents(subDiscussion.getContents())
                                                    .build()
                                    )
                                    .collect(Collectors.toList())
                    );
                    return fullDiscussion;
                }).collect(Collectors.toList());
    }

}
