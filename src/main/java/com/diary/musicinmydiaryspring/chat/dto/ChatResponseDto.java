package com.diary.musicinmydiaryspring.chat.dto;

import com.diary.musicinmydiaryspring.song.entity.Song;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatResponseDto {
    @JsonProperty("id")
    private Long id;

//    @JsonProperty("song")
//    private SongDto song;

    @JsonProperty("chat_response")
    private String response;
}
