package com.diary.musicinmydiaryspring.diary.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiaryRequestDto {
    private String content;
}
