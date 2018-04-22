package org.ljl.look.api.controller;

import org.ljl.look.api.dto.FullActivityWithAudit;
import org.ljl.look.api.service.ActivityWithAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity-with-audit")
public class FullActivityWithAuditController {

    @Autowired
    private ActivityWithAuditService activityWithAuditService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public FullActivityWithAudit get(@RequestParam("uuid") String uuid) {
        return activityWithAuditService.get(uuid);
    }

}
