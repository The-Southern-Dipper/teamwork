package com.southdipper.teamwork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
负责人：黄俊杰
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/test")
    public String test() {
        return "Hello";
    }
}
