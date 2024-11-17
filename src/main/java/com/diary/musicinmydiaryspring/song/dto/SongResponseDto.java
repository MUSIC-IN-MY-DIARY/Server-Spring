package com.diary.musicinmydiaryspring.song.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongResponseDto {
    @JsonProperty("song_id")
    private Long id;

    @JsonProperty("image_id")
    private Long imageId;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("song_title")
    private String songTitle;

    @JsonProperty("genre")
    private String genre;
}
