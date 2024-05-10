package com.southdipper.teamwork.service;

public interface JwtVerifyService {
    void verify(String token) throws Exception;
}
