package com.diary.musicinmydiaryspring.diary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DiaryResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nick_name")
    private String nickName;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("content")
    private String content;

    @JsonProperty("chat_response")
    private String chatResponse;
}
