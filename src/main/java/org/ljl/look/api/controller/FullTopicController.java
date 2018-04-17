package org.ljl.look.api.controller;

import org.ljl.look.api.dto.FullTopic;
import org.ljl.look.api.service.FullTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/full-topic")
public class FullTopicController {

    @Autowired
    private FullTopicService fullTopicService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("s")
    @ResponseStatus(HttpStatus.OK)
    public List<FullTopic> gets(@RequestHeader("token") String token, @RequestParam String parentTopicUuid, @RequestParam String pageInfoJsonStr) {
        String fromUser = stringRedisTemplate.opsForValue().get(token);
        return fullTopicService.get(fromUser, parentTopicUuid, pageInfoJsonStr);
    }

}
