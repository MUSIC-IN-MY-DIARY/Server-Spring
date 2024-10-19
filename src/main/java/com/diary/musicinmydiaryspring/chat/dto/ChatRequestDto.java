package com.diary.musicinmydiaryspring.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatRequestDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("chat_response")
    private String response;
}
