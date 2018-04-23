package org.ljl.look.api.controller;

import org.ljl.look.api.dto.TopicWithAudit;
import org.ljl.look.api.service.TopicWithAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicWithAuditController {

    @Autowired
    private TopicWithAuditService topicWithAuditService;

    @GetMapping("/api/topic-with-audit")
    @ResponseStatus(HttpStatus.OK)
    public TopicWithAudit get(@RequestParam("uuid") String uuid) {
        return topicWithAuditService.get(uuid);
    }

    @GetMapping("/api/user/topic-with-audit/s")
    @ResponseStatus(HttpStatus.OK)
    public List<TopicWithAudit> get(@RequestHeader("token") String token,
                                    @RequestParam("state") String state,
                                    @RequestParam("pageInfoJsonStr") String pageInfoJsonStr) {
        return topicWithAuditService.getsUserTopicWithAuditByState(token, state, pageInfoJsonStr);
    }

    @PutMapping("/api/user/topic-with-audit")
    @ResponseStatus(HttpStatus.OK)
    public void put(@RequestHeader("token") String token, @RequestBody TopicWithAudit topicWithAudit) {
        topicWithAuditService.update(token, topicWithAudit);
    }

}
