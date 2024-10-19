package com.diary.musicinmydiaryspring.song.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SongResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("album_title")
    private String albumTitle;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("song_title")
    private String songTitle;

    @JsonProperty("lyrics")
    private String lyrics;

    @JsonProperty("genre")
    private String genre;
}
