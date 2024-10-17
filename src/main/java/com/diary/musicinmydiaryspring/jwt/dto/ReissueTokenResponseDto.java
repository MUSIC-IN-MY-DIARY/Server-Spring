package com.diary.musicinmydiaryspring.jwt.dto;

import com.diary.musicinmydiaryspring.jwt.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenResponseDto {
    private String accessToken;
    private String refreshToken;

    public static ReissueTokenResponseDto of(Token token, String newAccessToken){
        return new ReissueTokenResponseDto(newAccessToken, token.getRefreshToken());
    }
}
