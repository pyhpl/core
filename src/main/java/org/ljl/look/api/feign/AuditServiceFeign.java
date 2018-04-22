package org.ljl.look.api.feign;

import org.ljl.look.api.entity.TopicAudit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient("audit")
public interface AuditServiceFeign {

    // ************************ /api/topic-audit ************************ //

    @GetMapping("/api/topic-audit")
    TopicAudit getTopicAuditByTopicUuid(@RequestParam("topicUuid") String topicUuid);

    @GetMapping("/api/activity-audit")
    TopicAudit getActivityAuditByActivityUuid(@RequestParam("activityUuid") String activityUuid);
}
