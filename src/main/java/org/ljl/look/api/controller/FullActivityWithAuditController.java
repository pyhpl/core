package org.ljl.look.api.controller;

import org.ljl.look.api.dto.FullActivityWithAudit;
import org.ljl.look.api.service.ActivityWithAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FullActivityWithAuditController {

    @Autowired
    private ActivityWithAuditService activityWithAuditService;

    @GetMapping("/api/activity-with-audit")
    @ResponseStatus(HttpStatus.OK)
    public FullActivityWithAudit get(@RequestParam("uuid") String uuid) {
        return activityWithAuditService.get(uuid);
    }

    @GetMapping("/api/user/activity-with-audit/s")
    @ResponseStatus(HttpStatus.OK)
    public List<FullActivityWithAudit> get(@RequestHeader("token") String token,
                                           @RequestParam("state") String state,
                                           @RequestParam("pageInfoJsonStr") String pageInfoJsonStr) {
        return activityWithAuditService.getsByState(token, state, pageInfoJsonStr);
    }

    @PutMapping("/api/user/activity-with-audit")
    @ResponseStatus(HttpStatus.OK)
    public void put(@RequestHeader("token") String token, @RequestBody FullActivityWithAudit fullActivityWithAudit) {
        activityWithAuditService.update(token, fullActivityWithAudit);
    }

}
