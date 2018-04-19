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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public List<FullTopic> getByParentTopic(String token, String parentTopicUuid, String pageInfoJsonStr) {
        List<Topic> topics = activityServiceFeign.getsTopicByParentTopicUuid(parentTopicUuid, pageInfoJsonStr);
        return mapToFullTopicAndMarkUserFocus(
                stringRedisTemplate.opsForValue().get(token),
                topics
        );
    }

    public List<FullTopic> getByKey(String token, String key, String pageInfoJsonStr) {
        List<Topic> topics = activityServiceFeign.getsTopicByKey(key, pageInfoJsonStr);
        return mapToFullTopicAndMarkUserFocus(
                stringRedisTemplate.opsForValue().get(token),
                topics
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
            List<Topic> topics = activityServiceFeign.getsTopicByCreateUser(token, pageInfoJsonStr);
            return mapToFullTopic(
                    topics,
                    userServiceFeign.getTopicFocuses(
                            true,
                            topics.stream()
                                    .map(topic ->
                                            TopicFocus.builder().fromUser(
                                                    stringRedisTemplate.opsForValue().get(token)
                                            ).topicUuid(topic.getUuid()).build()
                                    )
                                    .collect(Collectors.toList())
                    )
            );
        } else {
            return null;
        }
    }

    private List<FullTopic> mapToFullTopicAndMarkUserFocus(String user, List<Topic> topics) {
        // 如果用户未登录，则直接返回
        if (user == null) {
            return mapToFullTopic(topics, null);
        }
        return mapToFullTopic(
                topics,
                userServiceFeign.getTopicFocuses(
                        true,
                        topics.stream()
                                .map(topic ->
                                        TopicFocus.builder().fromUser(user).topicUuid(topic.getUuid()).build()
                                )
                                .collect(Collectors.toList())
                )
        );
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
