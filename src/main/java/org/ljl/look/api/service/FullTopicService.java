package org.ljl.look.api.service;

import org.ljl.look.api.configuration.ConstConfig;
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
        List<Topic> topics = activityServiceFeign.getsTopicByParentTopicUuid(parentTopicUuid, pageInfoJsonStr);
        // 如果用户未登录，则直接返回
        if (fromUser == null) {
            return mapToFullTopic(topics, null);
        }
        return mapToFullTopic(
                topics,
                userServiceFeign.getTopicFocuses(
                        true,
                        topics.stream()
                                .map(topic ->
                                        TopicFocus.builder().fromUser(fromUser).topicUuid(topic.getUuid()).build()
                                )
                                .collect(Collectors.toList())
                )
        );
    }

    public List<FullTopic> getsUserFullTopicByFeature(String token, String feature, String pageInfoJsonStr) {
        if (feature.equals(ConstConfig.FOCUS_FEATURE)) {
            List<TopicFocus> topicFocusList = userServiceFeign.getsTopicFocusByFromUser(token, pageInfoJsonStr);
            return mapToFullTopic(
                    activityServiceFeign.getsTopicByUuidList(
                            topicFocusList.stream()
                                    .map(TopicFocus::getTopicUuid)
                                    .reduce((e1, e2) -> e1 + "," + e2)
                                    .get()
                    ),
                    topicFocusList
            );
        } else if (feature.equals(ConstConfig.PUBLISH_FEATURE)) {
            return mapToFullTopic(activityServiceFeign.getsTopicByFromUser(token, pageInfoJsonStr), null);
        } else {
            return null;
        }
    }

    private List<FullTopic> mapToFullTopic(List<Topic> topics, List<TopicFocus> topicFocuses) {
        return topics.stream().map(topic -> {
                    FullTopic fullTopic = new FullTopic();
                    ReflectTool.copyCommonPropertyValue(fullTopic, topic);
                    fullTopic.setFocused(false);
                    if (topicFocuses != null) {
                        topicFocuses.forEach(focusedTopic -> {
                            if (fullTopic.getUuid().equals(focusedTopic.getTopicUuid())) {
                                fullTopic.setFocused(true);
                                fullTopic.setTopicFocusUuid(focusedTopic.getUuid());
                            }
                        });
                    }
                    return fullTopic;
                })
                .collect(Collectors.toList());
    }

}
