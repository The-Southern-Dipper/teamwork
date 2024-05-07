package com.southdipper.teamwork.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{2,10}$")
    private String nickname;

    @NotEmpty
    @Email
    private String email;
    private String userImg;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
    private Integer power;
}
