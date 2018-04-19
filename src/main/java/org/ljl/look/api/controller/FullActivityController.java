package org.ljl.look.api.controller;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.dto.FullActivity;
import org.ljl.look.api.service.FullActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FullActivityController {

    @Autowired
    private FullActivityService fullActivityService;

    @GetMapping("/api/full-activity/s")
    @ResponseStatus(HttpStatus.OK)
    public List<FullActivity> getsByKey(@RequestParam("key") String key,
                                        @RequestParam String pageInfoJsonStr) {
        return fullActivityService.getsFullActivityByKey(key, pageInfoJsonStr);
    }

    @GetMapping("/api/user/full-activity/s")
    @ResponseStatus(HttpStatus.OK)
    public List<FullActivity> getsByUser(@RequestHeader("token") String token,
                                         @RequestParam(ConstConfig.FEATURE) String feature,
                                         @RequestParam String pageInfoJsonStr) {
        return fullActivityService.getsUserFullActivityByFeature(token, feature, pageInfoJsonStr);
    }
}
