package org.ljl.look.api.feign;

import org.ljl.look.api.entity.Discussion;
import org.ljl.look.api.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
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

    // ********************** /api/discussion ******************** //
    @GetMapping("/api/discussion/s")
    List<Discussion> getsByBelongToActivity(@RequestParam("belongToActivity") String belongToActivity);

    @GetMapping("/api/discussion/count")
    int countDiscussionByBelongToActivity(@RequestParam("belongToActivity") String belongToActivity);

    // ********************** /api/activity-like ******************** //
    @GetMapping("/api/activity-like/count")
    int countActivityLikeByActivityUuid(@RequestParam("activityUuid") String activityUuid);
}









