package com.southdipper.teamwork.controller;

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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            /**发送验证码,调用接口
             *
             *
             *
             */
            //验证代码
            if(!redisService.checkEmailCaptcha(email,captcha)){
                return Result.error("验证码错误");
            }
            //注册到数据库
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
    public Result getInfo() {
        String username = ThreadLocalUtil.getUsername();
        User user = userService.getByUserName(username);
        return Result.success(user);
    }

    @PatchMapping("/changeEmail")
    public Result changeEmail(@Email String email) {
        String username = ThreadLocalUtil.getUsername();
        User user = userService.getByUserName(username);
        //检查邮箱是否被使用
        if(userService.getByEmail(email)!=null){
            return Result.error("邮箱已被使用");
        }
        user.setEmail(email);
        userService.updateMsg(user);
        return Result.success();
    }

    @PatchMapping("/changeNickname")
    public Result changeNickName(@Pattern(regexp = "^\\S{2,10}$")String nickname) {
        String username = ThreadLocalUtil.getUsername();
        User user = userService.getByUserName(username);
        //检查nickname是否已存在
        if (userService.getByNickName(nickname)!=null){
            return Result.error("昵称已存在!");
        }
        user.setNickname(nickname);
        userService.updateMsg(user);
        return Result.success();
    }

    @PatchMapping("/changePwd")
    public Result changePassword(@Pattern(regexp = "^\\S{5,16}$")String oldPwd,
                                 @Pattern(regexp = "^\\S{5,16}$")String newPwd,
                                 @Pattern(regexp = "^\\S{5,16}$")String confirmPwd,
                                 @Pattern(regexp = "^\\S{6}$")String captcha) {
        String username = ThreadLocalUtil.getUsername();
        User user = userService.getByUserName(username);
        if(!user.getPassword().equals(MD5Util.md5(oldPwd))) {
            return Result.error("原密码错误");
        }
        if(!newPwd.equals(confirmPwd)) {
            return Result.error("两次输入的密码不相同");
        }
        /**发送验证码,调用接口
         *
         *
         *
         */
        //检查验证码
        if(!redisService.checkEmailCaptcha(user.getEmail(),captcha)){
            return Result.error("验证码错误");
        }
        //删除token，再写入数据库，让用户重新登陆
        redisService.deleteJWT(username);
        userService.changePwd(username, MD5Util.md5(newPwd));
        return Result.success();
    }

    @PostMapping("/captcha")
    public Result sendEmail(@Email String email){
        //获取验证码
        String captcha = EmailUtil.sendCaptcha(email);
        //将邮箱与验证码存入redis
        redisService.saveEmailCaptcha(email,captcha);
        return Result.success("验证码发送成功");
    }

}

