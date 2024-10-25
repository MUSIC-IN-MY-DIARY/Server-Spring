package com.diary.musicinmydiaryspring.chat.dto;

import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import com.diary.musicinmydiaryspring.song.entity.Song;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ChatResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("chat_response")
    private String chatResponse;
}
