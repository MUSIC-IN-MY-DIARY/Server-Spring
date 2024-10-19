package com.diary.musicinmydiaryspring.song.dto;

import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SongRequestDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("diary")
    private DiaryResponseDto diaryResponseDto;
}
