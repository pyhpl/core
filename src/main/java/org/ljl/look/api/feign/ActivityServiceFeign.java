package org.ljl.look.api.feign;

import org.ljl.look.api.entity.Activity;
import org.ljl.look.api.entity.ActivityImage;
import org.ljl.look.api.entity.Topic;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@FeignClient("activity")
public interface ActivityServiceFeign {

    // ************************ /api/activity ************************ //

    @GetMapping("/api/activity")
    Activity getActivity(@RequestParam("uuid") String uuid);

    @GetMapping("/api/activity/s")
    List<Activity> getActivitiesByUuidList(@RequestParam("uuidList") String uuidList);

    @GetMapping("/api/activity/s")
    List<Activity> getActivitiesByKey(@RequestParam("key") String key, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    @GetMapping("/api/user/activity/s")
    List<Activity> getActivitiesByPublishUser(@RequestHeader("token") String token, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    // ************************ /api/topic ************************ //

    @GetMapping("/api/topic")
    Topic getTopic(@RequestParam("uuid") String uuid);

    @GetMapping("/api/topic/s")
    List<Topic> getsTopicByParentTopicUuid(@RequestParam("parentTopicUuid") String parentTopicUuid, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    @GetMapping("/api/topic/s")
    List<Topic> getsTopicByUuidList(@RequestParam("uuidList") String uuidList);

    @GetMapping("/api/user/topic/s")
    List<Topic> getsTopicByCreateUser(@RequestHeader("token") String token, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    @GetMapping("/api/topic/s")
    List<Topic> getsTopicByKey(@RequestParam("key") String key, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    // ************************ /api/activity-image ************************ //

    @GetMapping("/api/activity-image")
    List<ActivityImage> getActivityImagesByActivityUuid(@RequestParam("activityUuid") String activityUuid);
}
