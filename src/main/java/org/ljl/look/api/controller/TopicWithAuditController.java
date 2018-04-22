package org.ljl.look.api.controller;

import org.ljl.look.api.dto.TopicWithAudit;
import org.ljl.look.api.service.TopicWithAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topic-with-audit")
public class TopicWithAuditController {

    @Autowired
    private TopicWithAuditService topicWithAuditService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public TopicWithAudit get(@RequestParam("uuid") String uuid) {
        return topicWithAuditService.get(uuid);
    }

}
