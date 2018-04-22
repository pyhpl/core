package org.ljl.look.api.service;

import org.ljl.look.api.dto.FullActivityWithAudit;
import org.ljl.look.api.feign.ActivityServiceFeign;
import org.ljl.look.api.feign.AuditServiceFeign;
import org.ljl.look.api.util.ReflectTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityWithAuditService {

    @Autowired
    private AuditServiceFeign auditServiceFeign;

    @Autowired
    private FullActivityService fullActivityService;

    public FullActivityWithAudit get(String uuid) {
        FullActivityWithAudit fullActivityWithAudit = new FullActivityWithAudit();
        fullActivityWithAudit.setFullActivity(
                fullActivityService.getByUuid(uuid)
        );
        ReflectTool.copyCommonPropertyValue(
                fullActivityWithAudit,
                auditServiceFeign.getActivityAuditByActivityUuid(uuid)
        );
        return fullActivityWithAudit;
    }

}
