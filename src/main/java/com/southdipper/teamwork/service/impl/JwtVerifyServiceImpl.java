package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.service.JwtVerifyService;
import com.southdipper.teamwork.service.RedisService;
import com.southdipper.teamwork.util.JwtUtil;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtVerifyServiceImpl implements JwtVerifyService {
    @Autowired
    RedisService redisService;
    @Override
    public void verify(String token) throws Exception {
        // JWT是否被篡改
        if(JwtUtil.verify(token)) {
            if(!redisService.checkJWT(JwtUtil.getUsernameFromToken(token), token)) {
                throw new Exception("用户未登录");
            }
            ThreadLocalUtil.set(token);
        }
        else {
            throw new Exception("登录凭证过期或错误");
        }
    }
}
