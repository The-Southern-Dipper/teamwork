package com.southdipper.teamwork.controller;

import com.auth0.jwt.interfaces.Claim;
import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.pojo.User;
import com.southdipper.teamwork.service.RedisService;
import com.southdipper.teamwork.service.UserService;
import com.southdipper.teamwork.util.EmailUtil;
import com.southdipper.teamwork.util.JwtUtil;
import com.southdipper.teamwork.util.MD5Util;
import com.southdipper.teamwork.util.ThreadLocalUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
负责人：黄俊杰
 */
@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin // 支持跨域，axios可以访问下面的接口哦
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisService redisService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$")String username,
                           @Pattern(regexp = "^\\S{5,16}$")String password,
                           @Pattern(regexp = "^\\S{5,16}$")String confirmpassword,
                           @Pattern(regexp = "^\\S{6}$")String captcha,
                           @Email String email) {
        User user = userService.getByUserName(username);
        if(user == null) {
            //用户不存在，进入注册流程
            if(!password.equals(confirmpassword)) {
                return Result.error("两次输入的密码不相同");
            }
            userService.register(username, MD5Util.md5(password),email);
            return Result.success("注册成功");
        }
        else {
            return Result.error("用户已存在");
        }
    }

    @PostMapping("/login")
    public Result login(String username, String password) {
        // 先拿到用户的信息
        User user = userService.getByUserName(username);
        if(user == null) {
            return Result.error("用户不存在");
        }
        if(!user.getPassword().equals(MD5Util.md5(password))) {
            return Result.error("密码错误");
        }
        //生成用户token,并存入redis
        String token = JwtUtil.generateToken(user.getId(),user.getUsername());
        redisService.saveJWT(user.getUsername(),token);

        return Result.success(token);
    }

    @GetMapping("/getInfo")
    public Result getInfo(String Authorization) {

        Map<String, Claim> claims = ThreadLocalUtil.get();
        String username = claims.get("username").asString();
        User user = userService.getByUserName(username);
        return Result.success(user);
    }

    @PatchMapping("/changeMsg")
    public Result changeMsg(@Pattern(regexp = "^\\S{2,10}$")String nickname,
                            @Email String email) {
        Map<String, Claim> claims = ThreadLocalUtil.get();
        String username = claims.get("username").asString();
        User user = userService.getByUserName(username);
        user.setNickname(nickname);
        user.setEmail(email);
        userService.updateMsg(user);
        return Result.success();
    }

    @PatchMapping("/changePassword")
    public Result changePassword(@Pattern(regexp = "^\\S{5,16}$")String oldPwd,
                                 @Pattern(regexp = "^\\S{5,16}$")String newPwd,
                                 @Pattern(regexp = "^\\S{5,16}$")String confirmPwd,
                                 @RequestHeader(name = "Authorization")String token) {
        Map<String, Claim> claims = ThreadLocalUtil.get();
        String username = claims.get("username").asString();
        User user = userService.getByUserName(username);
        if(!user.getPassword().equals(MD5Util.md5(oldPwd))) {
            return Result.error("原密码错误");
        }
        if(!newPwd.equals(confirmPwd)) {
            return Result.error("两次输入的密码不相同");
        }
        SetOperations<String, String> operations = stringRedisTemplate.opsForSet();
        stringRedisTemplate.delete(username);
        // 把token删完再改密码防止他没删完抛出异常，但是密码已经改了
        userService.changePwd(username, MD5Util.md5(newPwd));
        return Result.success();
    }

    @PostMapping("/captcha")
    public Result sendEmail(@Email String email){
        //获取验证码
        String captcha = EmailUtil.sendCaptcha(email);
        //将验证码存入redis
        redisService.saveEmailCaptcha(1,captcha);
        return Result.success("发送成功");
    }

}

