package com.southdipper.teamwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TeamworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamworkApplication.class, args);
    }

}
