package com.southdipper.teamwork;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.southdipper.teamwork.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TeamworkApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generateJWT() {
        String TOKEN_SECRET = "privateKey";
        // 私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 设置Header的信息
        Map<String, Object> header = new HashMap<String, Object>(2);
        header.put("typ", "jwt");
        JWTCreator.Builder builder = JWT.create().
                withHeader(header).
                withIssuedAt(new Date());   // 获取发证时间
        String username = "Aderversa";
        Integer id = 1;
        // 只设置了声明但没有过期时间的JWT,测试时可以提交这个东西来测试。但可能需要数据库中有对应的用户
        builder.withClaim("username", username);
        builder.withClaim("id", id);
        String token = builder.sign(algorithm);
        System.out.println(token);
    }

}
