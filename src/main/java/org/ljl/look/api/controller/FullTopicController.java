package org.ljl.look.api.controller;

import org.ljl.look.api.configuration.ConstConfig;
import org.ljl.look.api.dto.FullTopic;
import org.ljl.look.api.service.FullTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FullTopicController {

    @Autowired
    private FullTopicService fullTopicService;

    @GetMapping("/api/full-topic/s")
    @ResponseStatus(HttpStatus.OK)
    public List<FullTopic> gets(@RequestHeader(value = "token", required = false, defaultValue = "") String token,
                                @RequestParam(required = false) String parentTopicUuid,
                                @RequestParam(required = false) String key,
                                @RequestParam String pageInfoJsonStr) {
        if (parentTopicUuid != null) { // 根据父主题获取子主题
            return fullTopicService.getByParentTopic(token, parentTopicUuid, pageInfoJsonStr);
        } else if (key != null) { // 根据关键字获取子主题
            return fullTopicService.getByKey(token, key, pageInfoJsonStr);
        } else {
            return null;
        }
    }

    @GetMapping("/api/user/full-topic/s")
    @ResponseStatus(HttpStatus.OK)
    public List<FullTopic> getsByUser(@RequestHeader("token") String token,
                                      @RequestParam(ConstConfig.FEATURE) String feature,
                                      @RequestParam String pageInfoJsonStr) {
        return fullTopicService.getsUserFullTopicByFeature(token, feature, pageInfoJsonStr);
    }
}
