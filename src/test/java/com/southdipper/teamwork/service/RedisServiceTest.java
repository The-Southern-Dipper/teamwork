package com.southdipper.teamwork.service;

import com.southdipper.teamwork.util.EmailUtil;
import com.southdipper.teamwork.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/*
负责人：张永祥
邮箱：2457699535@qq.com
 */
@SpringBootTest
public class RedisServiceTest {
    @Autowired
    private RedisService redisService;
    @Test
    public void saveJWTTest() {
        Integer id = 1;
        String username = "Aderversa";
        String token = JwtUtil.generateToken(id, username);
        redisService.saveJWT(username, token);
        if(!JwtUtil.verify(token)) {
            System.out.println("JWT令牌验证失败");
        }
        if(!redisService.checkJWT(username, token)) {
            System.out.println("JWT令牌不存在于Redis缓存中");
        }
        redisService.deleteJWT(username);
        String reciever = "2457699535@qq.com";
        String captcha = EmailUtil.sendCaptcha(reciever);
        redisService.saveEmailCaptcha(String.valueOf(id), captcha);
        if(!redisService.checkEmailCaptcha(String.valueOf(id), captcha)) {
            System.out.println("邮箱验证码不存在于Redis缓冲中");
        }
    }
}
