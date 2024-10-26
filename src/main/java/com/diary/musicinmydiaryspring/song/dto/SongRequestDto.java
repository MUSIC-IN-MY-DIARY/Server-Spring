package com.diary.musicinmydiaryspring.song.dto;

import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SongRequestDto {
    @JsonProperty("song_id")
    private Long id;

    @JsonProperty("album_id")
    private Long albumId;
}
