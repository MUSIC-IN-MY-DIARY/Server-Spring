package com.diary.musicinmydiaryspring.diary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiaryRequestDto {
    private Long id;
    private String nickName;
    private String content;
}
