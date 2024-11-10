package com.diary.musicinmydiaryspring.song.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SongResponseDto {
    @JsonProperty("song_id")
    private Long id;

    @JsonProperty("album_title")
    private String albumTitle;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("song_title")
    private String songTitle;

    @JsonProperty("genre")
    private String genre;
}
