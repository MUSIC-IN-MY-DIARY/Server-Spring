package com.diary.musicinmydiaryspring.member.dto;

import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String nickname;
    private String profile;
}
