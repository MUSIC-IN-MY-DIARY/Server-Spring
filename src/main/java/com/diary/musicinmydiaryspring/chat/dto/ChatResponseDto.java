package com.diary.musicinmydiaryspring.chat.dto;

import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import com.diary.musicinmydiaryspring.song.entity.Song;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("song_response")
    private SongResponseDto songResponseDto;

    @JsonProperty("chat_response")
    private String chatResponse;
}
