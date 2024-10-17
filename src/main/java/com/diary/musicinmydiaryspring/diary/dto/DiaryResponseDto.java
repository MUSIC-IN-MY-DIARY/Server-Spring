package com.diary.musicinmydiaryspring.diary.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DiaryResponseDto {
    private Long id;
    private String nickName;
    private LocalDateTime createdAt;
    private String content;
}
