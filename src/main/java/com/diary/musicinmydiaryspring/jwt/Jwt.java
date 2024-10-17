package com.diary.musicinmydiaryspring.jwt;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Jwt {
    private final String accessToken;
    private final String refreshToken;

    private final LocalDateTime accessTokenExpriationTime;
    private final LocalDateTime refreshTokenExpriation;

    public Jwt(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpriationTime = null;
        this.refreshTokenExpriation = null;
    }
}
