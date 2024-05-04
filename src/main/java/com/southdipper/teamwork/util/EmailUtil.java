package com.southdipper.teamwork.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/*
* 负责人：张永祥
* */
public class EmailUtil {
    public static String emailServer = "smtp.qq.com";
    public static String sender = "2457699535@qq.com";
    public static String authCode = "ayolpgztzpvxeagc";

    // 生成6位数字的随机验证码
    private static String generateChaptcha() {
        int N = 6;
        StringBuilder chaptcha = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            chaptcha.append(rand.nextInt(10));
        }
        return chaptcha.toString();
    }
    // 发送验证码给指定邮箱，并返回发送的验证码
    public static String sendCaptcha(String reciever) {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailServer);
        Session session = Session.getInstance(props, null);
        String chaptcha = generateChaptcha();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom("The Southern Dipper Team"+"<"+sender+">");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
            message.setSubject("朝书夕拾平台验证码");
            message.setText("您的验证码为："+chaptcha+"， 该验证码将于十分钟后过期，请及时使用。");
            Transport.send(message, sender, authCode);
        }
        catch (MessagingException e) {
            System.out.println("邮件发送失败，原因为：" + e.getMessage());
            return null;
        }
        return chaptcha;
    }
}
