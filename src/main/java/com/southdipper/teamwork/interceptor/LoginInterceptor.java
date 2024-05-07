package com.southdipper.teamwork.interceptor;

import com.southdipper.teamwork.service.RedisService;
import com.southdipper.teamwork.util.JwtUtil;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/*
负责人：张永祥
除了访问登录、注册和发送邮箱验证码这几个接口外，其他接口都必须要携带JWT令牌作为用户身份的标识
登录拦截器LoginInterceptor就是为此而存在的，防止有人没有JWT令牌还想使用网站的服务
 */
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

