package org.ljl.look.api.feign;

import org.ljl.look.api.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@FeignClient("user")
public interface UserServiceFeign {

    // ************************ /api/user ************************ //
    @GetMapping("/api/user")
    User getUser(@RequestParam("openId") String openId);

    // ************************ /api/join ************************ //
    @GetMapping("/api/join/count")
    int countJoinByActivityUuid(@RequestParam("activityUuid") String activityUuid);

    @GetMapping("/api/user/join/s")
    List<Join> getsJoinByFromUser(@RequestHeader("token") String token, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    // ********************** /api/discussion ******************** //
    @GetMapping("/api/discussion/s")
    List<Discussion> getsByBelongToActivity(@RequestParam("belongToActivity") String belongToActivity);

    @GetMapping("/api/discussion/count")
    int countDiscussionByBelongToActivity(@RequestParam("belongToActivity") String belongToActivity);

    // ********************** /api/activity-like ******************** //
    @GetMapping("/api/activity-like/count")
    int countActivityLikeByActivityUuid(@RequestParam("activityUuid") String activityUuid);

    // ********************** /api/topic-focus ******************** //
    @RequestMapping("/api/topic-focus/s")
    List<TopicFocus> getTopicFocuses(@RequestParam("focused") boolean focused, @RequestBody List<TopicFocus> topicFocuses);

    @GetMapping("/api/user/topic-focus/s")
    List<TopicFocus> getsTopicFocusByFromUser(@RequestHeader("token") String token, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    // ********************** /api/activity-focus ******************** //
    @GetMapping("/api/user/activity-focus/s")
    List<ActivityFocus> getsActivityFocusByFromUser(@RequestHeader("token") String token, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);
}

