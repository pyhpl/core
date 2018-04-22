package org.ljl.look.api.service;

import org.ljl.look.api.dto.TopicWithAudit;
import org.ljl.look.api.entity.TopicAudit;
import org.ljl.look.api.feign.ActivityServiceFeign;
import org.ljl.look.api.feign.AuditServiceFeign;
import org.ljl.look.api.util.ReflectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicWithAuditService {

    @Autowired
    private AuditServiceFeign auditServiceFeign;
    @Autowired
    private ActivityServiceFeign activityServiceFeign;

    public TopicWithAudit get(String uuid) {
        TopicWithAudit topicWithAudit = new TopicWithAudit();
        ReflectTool.copyCommonPropertyValue(
                topicWithAudit,
                auditServiceFeign.getTopicAuditByTopicUuid(uuid)
        );
        ReflectTool.copyCommonPropertyValue(
                topicWithAudit,
                activityServiceFeign.getTopic(uuid)
        );

        return topicWithAudit;
    }

}
