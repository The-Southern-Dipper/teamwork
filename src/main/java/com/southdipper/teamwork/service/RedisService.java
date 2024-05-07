package com.southdipper.teamwork.service;

/*
负责人：张永祥
 */
public interface RedisService {
    /*
    将JWT令牌存入键名为username的Set中，并设置过期时间
    username : 用户名
    token : JWT令牌
     */
    void saveJWT(String username, String token);
    /*
    删除Redis中键名为username的键
     */
    void deleteJWT(String username);
    /*
    检查Redis中键名为username的Set中是否存在token，并刷新该集合的过期时间
     */
    boolean checkJWT(String username, String token);
    /*
    将键名为id，值为captcha的键值对存入Redis
     */
    void saveEmailCaptcha(String email, String captcha);
    /*
    检查验证码是否正确
     */
    boolean checkEmailCaptcha(String email, String captcha);
}
