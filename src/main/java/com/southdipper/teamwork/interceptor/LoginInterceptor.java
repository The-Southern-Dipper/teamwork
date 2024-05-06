package com.southdipper.teamwork.interceptor;

import com.southdipper.teamwork.service.RedisService;
import com.southdipper.teamwork.util.JwtUtil;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    RedisService redisService;
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        String token = request.getParameter("Authorization");
        // token没有被篡改
        if(JwtUtil.verify(token)) {
            //  Redis中有没有？
            if(!redisService.checkJWT(JwtUtil.getUsernameFromToken(token), token)) {
                // Redis中不存在该token
                response.setStatus(401);
                return false;
            }
            ThreadLocalUtil.set(token);
            return true;
        }
        else {
            response.setStatus(401);
            return false;
        }
    }
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
        ThreadLocalUtil.remove();
    }
}

