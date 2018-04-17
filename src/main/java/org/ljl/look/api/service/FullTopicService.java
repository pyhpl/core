package org.ljl.look.api.service;

import org.ljl.look.api.dto.FullTopic;
import org.ljl.look.api.entity.Topic;
import org.ljl.look.api.entity.TopicFocus;
import org.ljl.look.api.feign.ActivityServiceFeign;
import org.ljl.look.api.feign.UserServiceFeign;
import org.ljl.look.api.util.ReflectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FullTopicService {

    @Autowired
    private ActivityServiceFeign activityServiceFeign;
    @Autowired
    private UserServiceFeign userServiceFeign;

    public List<FullTopic> get(String fromUser, String parentTopicUuid, String pageInfoJsonStr) {
        List<Topic> topics = activityServiceFeign.getsTopic(parentTopicUuid, pageInfoJsonStr);
        List<FullTopic> fullTopics = topics.stream().map(topic -> {
                    FullTopic fullTopic = new FullTopic();
                    ReflectTool.copyCommonPropertyValue(fullTopic, topic);
                    fullTopic.setFocused(false);
                    return fullTopic;
                })
                .collect(Collectors.toList());
        // 如果用户未登录，则直接返回
        if (fromUser == null) {
            return fullTopics;
        }
        List<TopicFocus> focusedTopics =
                userServiceFeign.getTopicFocuses(
                        true,
                        topics.stream()
                                .map(topic ->
                                        TopicFocus.builder().fromUser(fromUser).topicUuid(topic.getUuid()).build()
                                )
                                .collect(Collectors.toList())
                );
        fullTopics.forEach(fullTopic ->
            focusedTopics.forEach(focusedTopic -> {
                if (fullTopic.getUuid().equals(focusedTopic.getTopicUuid())) {
                    fullTopic.setFocused(true);
                    fullTopic.setTopicFocusUuid(focusedTopic.getTopicUuid());
                }
            })
        );

        return fullTopics;
    }

}
