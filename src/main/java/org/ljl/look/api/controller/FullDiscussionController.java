package org.ljl.look.api.controller;

import org.ljl.look.api.dto.FullDiscussion;
import org.ljl.look.api.service.FullDiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/full-discussion")
public class FullDiscussionController {

    @Autowired
    private FullDiscussionService fullDiscussionService;

    @GetMapping("s")
    @ResponseStatus(HttpStatus.OK)
    public List<FullDiscussion> getsByBelongToActivity(@RequestParam String belongToActivity) {
        return fullDiscussionService.getsByBelongToActivity(belongToActivity);
    }
}
