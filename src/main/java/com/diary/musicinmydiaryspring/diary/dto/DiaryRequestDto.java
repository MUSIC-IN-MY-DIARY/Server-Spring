package com.diary.musicinmydiaryspring.diary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {
    @JsonProperty("diaryContent")
    private String diaryContent;
}
