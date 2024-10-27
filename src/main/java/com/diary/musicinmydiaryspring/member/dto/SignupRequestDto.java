package com.diary.musicinmydiaryspring.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignupRequestDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @JsonProperty("nickname")
    private String nickname;

    public boolean isPasswordConfirmed(){
        return password.equals(confirmPassword);
    }
}
