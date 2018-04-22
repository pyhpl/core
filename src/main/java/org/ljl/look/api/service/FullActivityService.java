package org.ljl.look.api.service;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.dto.FullActivity;
import org.ljl.look.api.entity.*;
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

    /** 根据关键字获取活动详情 */
    public List<FullActivity> getsFullActivityByKey(String key, String pageInfoJsonStr) {
        return mapToFullActivities(activityServiceFeign.getActivitiesByKey(key, pageInfoJsonStr));
    }

    /** 根据特征获取活动详情 */
    public List<FullActivity> getsUserFullActivityByFeature(String token, String feature, String pageInfoJsonStr) {
        if (feature.equals(ConstConfig.JOIN_FEATURE)) {
            List<Join> joins = userServiceFeign.getsJoinByFromUser(token, pageInfoJsonStr);
            if (joins == null) {
                return null;
            }
            return mapToFullActivities(
                    activityServiceFeign.getActivitiesByUuidList(
                            joins.stream()
                                    .map(Join::getActivityUuid)
                                    .reduce((e1, e2) -> e1 + "," + e2)
                                    .get()
                    )
            );
        } else if (feature.equals(ConstConfig.FOCUS_FEATURE)) {
            List<ActivityFocus> activityFocusList = userServiceFeign.getsActivityFocusByFromUser(token, pageInfoJsonStr);
            if (activityFocusList == null) { // 没有关注的活动，直接返回
                return null;
            }
            return mapToFullActivities(
                    activityServiceFeign.getActivitiesByUuidList(
                            activityFocusList.stream()
                                    .map(ActivityFocus::getActivityUuid)
                                    .reduce((e1, e2) -> e1 + "," + e2)
                                    .get()
                    )
            );
        } else if (feature.equals(ConstConfig.PUBLISH_FEATURE)) {
            return mapToFullActivities(
                    activityServiceFeign.getActivitiesByPublishUser(token, pageInfoJsonStr)
            );
        } else {
            return null;
        }
    }

    /** 根据UUID获取活动详情 */
    public FullActivity getByUuid(String uuid) {
        return mapToFullActivity(activityServiceFeign.getActivity(uuid));
    }

    private List<FullActivity> mapToFullActivities(List<Activity> activities) {
        return activities.stream().map(
            this::mapToFullActivity
        ).collect(Collectors.toList());
    }

    private FullActivity mapToFullActivity(Activity activity) {
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
    }
}
