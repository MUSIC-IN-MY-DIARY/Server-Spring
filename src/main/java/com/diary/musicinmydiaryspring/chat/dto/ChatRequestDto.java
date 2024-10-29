package com.diary.musicinmydiaryspring.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ChatRequestDto {
    @JsonProperty("diary_content")
    private String diaryContent;
}
