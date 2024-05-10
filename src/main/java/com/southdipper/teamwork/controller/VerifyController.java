package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.Result;
import com.southdipper.teamwork.service.JwtVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class VerifyController {
    @Autowired
    JwtVerifyService jwtVerifyService;
    @RequestMapping("/verify")
    public Result verifyToken(@RequestHeader("Authorization")String token) {
        try {
            jwtVerifyService.verify(token);
            return Result.success("验证成功");
        }
        catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
