package org.ljl.look.api.interpretor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello ");
        String token = request.getHeader("token");
//        return  token != null && stringRedisTemplate.hasKey(token);
        return true;
    }
}
