package org.ljl.look.api.service;

import org.ljl.look.api.dto.FullActivity;
import org.ljl.look.api.entity.Activity;
import org.ljl.look.api.entity.ActivityImage;
import org.ljl.look.api.entity.Topic;
import org.ljl.look.api.entity.User;
import org.ljl.look.api.feign.ActivityServiceFeign;
import org.ljl.look.api.feign.UserServiceFeign;
import org.ljl.look.api.util.ReflectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FullActivityService {
    @Autowired
    private ActivityServiceFeign activityServiceFeign;
    @Autowired
    private UserServiceFeign userServiceFeign;

    public List<FullActivity> getsFullActivityByTag(String tag) {
        List<Activity> activities = activityServiceFeign.getActivitiesByTag(tag);
        return activities.stream().map(activity -> {
            /** 数据组合 */
            FullActivity fullActivity = new FullActivity();
            // 共同属性赋值
            ReflectTool.copyCommonPropertyValue(fullActivity, activity);
            // 获取活动图片
            List<ActivityImage> activityImages = activityServiceFeign.getActivityImagesByActivityUuid(activity.getUuid());
            fullActivity.setActivityImageUrls(
                    activityImages.stream().map(ActivityImage::getImage).collect(Collectors.toList())
            );
            // 获取发布用户
            User user = userServiceFeign.getUser(activity.getPublishUser());
            fullActivity.setPublishUserName(user.getName());
            fullActivity.setPublishUserAvatar(user.getAvatar());
            // 获取所属主题
            Topic topic = activityServiceFeign.getTopic(activity.getTopicUuid());
            fullActivity.setTopicName(topic.getName());
            // 获取评论数
            fullActivity.setDiscussionCount(
                    userServiceFeign.countDiscussionByBelongToActivity(activity.getUuid())
            );
            // 获取点赞数
            fullActivity.setLikeCount(
                    userServiceFeign.countActivityLikeByActivityUuid(activity.getUuid())
            );
            // 获取参与人数
            fullActivity.setJoinedPeopleCount(
                    userServiceFeign.countJoinByActivityUuid(activity.getUuid())
            );
            return fullActivity;
        }).collect(Collectors.toList());
    }
}
