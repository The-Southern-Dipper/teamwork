package com.southdipper.teamwork;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.southdipper.teamwork.mapper.BookMapper;
import com.southdipper.teamwork.pojo.Book;
import com.southdipper.teamwork.pojo.SelectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
这个文件因为冲突决定废弃.
可以读但不要写
所以，请不要在这个地方再写任何东西
请自行开启一个测试文件并标识你的署名
并且不要随意修改他人所写的文件
*/
@SpringBootTest
class TeamworkApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    BookMapper bookMapper;

    @Test
    void contextLoads() {
        SelectRequest selectRequest = new SelectRequest();
//        selectRequest.setContent("高等");
        selectRequest.setPriceEnd(50.50);
        selectRequest.setOrderRequest(SelectRequest.PRICE_ORDER_ASC);
        List<Book> bookList = bookMapper.search(selectRequest);
        bookList.stream().forEach(book -> System.out.println(book));
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
        stringRedisTemplate.opsForSet().add(username, token);
    }
}
