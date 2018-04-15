package org.ljl.look.api.controller;

import org.ljl.look.api.dto.FullActivity;
import org.ljl.look.api.service.FullActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/full-activity")
public class FullActivityController {

    @Autowired
    private FullActivityService fullActivityService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<FullActivity> getsByTag(@RequestParam("tag") String tag) {
        return fullActivityService.getFullActivityByTag(tag);
    }
}
