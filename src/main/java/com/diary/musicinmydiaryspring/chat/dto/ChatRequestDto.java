package com.diary.musicinmydiaryspring.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatRequestDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("song_id")
    private Long songId;

    @JsonProperty("diary_content")
    private String diaryContent;
}
