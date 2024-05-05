package com.southdipper.teamwork.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
* 测试负责人：张永祥
* */
@SpringBootTest
public class EmailUtilTest {
    // 测试发送验证码接口
    @Test
    public void sendChaptchaTest() {
        String chaptcha = EmailUtil.sendCaptcha("2457699535@aliyun.com");
        System.out.println(chaptcha);
    }
}
