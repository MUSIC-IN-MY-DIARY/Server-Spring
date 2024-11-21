package com.diary.musicinmydiaryspring.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {
    @JsonProperty("diaryContent")
    private String diaryContent;

    public static ChatRequestDto of(String content) {
        return ChatRequestDto.builder()
                .diaryContent(content)
                .build();
    }
}
