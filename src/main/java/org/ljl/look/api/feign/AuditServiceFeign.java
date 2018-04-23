package org.ljl.look.api.feign;

import org.ljl.look.api.entity.TopicAudit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@FeignClient("audit")
public interface AuditServiceFeign {

    // ************************ /api/topic-audit ************************ //

    @GetMapping("/api/topic-audit")
    TopicAudit getTopicAuditByTopicUuid(@RequestParam("topicUuid") String topicUuid);

    @GetMapping("/api/user/topic-audit/s")
    List<TopicAudit> getsTopicAuditUserAudited(@RequestHeader("token") String token, @RequestParam("pageInfoJsonStr") String pageInfoJsonStr);

    // ************************ /api/activity-audit ************************ //

    @GetMapping("/api/activity-audit")
    TopicAudit getActivityAuditByActivityUuid(@RequestParam("activityUuid") String activityUuid);

}
