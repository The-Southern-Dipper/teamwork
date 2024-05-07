package com.southdipper.teamwork.service.impl;

import com.southdipper.teamwork.service.RedisService;
import com.southdipper.teamwork.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/*
负责人：张永祥
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveJWT(String username, String token) {
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        operations.add(username, token);
        operations.getOperations().expire(username, JwtUtil.getExpireTime(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void deleteJWT(String username) {
        redisTemplate.delete(username);
    }

    // 查看Redis中username是否拥有token令牌
    @Override
    public boolean checkJWT(String username, String token) {
        SetOperations<String, String> operations = redisTemplate.opsForSet();
        if(Boolean.FALSE.equals(operations.isMember(username, token))){
            return false;
        }
        operations.getOperations().expire(username, JwtUtil.getExpireTime(), TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public void saveEmailCaptcha(String email, String captcha) {
        // 邮件验证码过期时间10分钟
        long expireTime = 10 * 60 * 1000;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(email, captcha, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean checkEmailCaptcha(String email, String captcha) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String redisCaptcha = operations.get(email);
        return redisCaptcha.equals(captcha);
    }
}
