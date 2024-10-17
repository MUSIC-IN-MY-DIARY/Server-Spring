package com.diary.musicinmydiaryspring.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenRequestDto {
    private String refreshToken;
}
