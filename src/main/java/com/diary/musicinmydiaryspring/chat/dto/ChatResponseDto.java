package com.diary.musicinmydiaryspring.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatResponseDto {
    @JsonProperty("chat_id")
    private Long id;

    @JsonProperty("diary_id")
    private Long diaryId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("is_liked")
    private Boolean isLiked;
}

