package com.diary.musicinmydiaryspring.bookmark.dto;

import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDetailRecommendResponseDto {
    @JsonProperty("bookmark_id")
    private Long id;

    @JsonProperty("songs")
    private List<SongResponseDto> songResponseDto;

    @JsonProperty("diary")
    private DiaryResponseDto diaryResponseDto;
}
