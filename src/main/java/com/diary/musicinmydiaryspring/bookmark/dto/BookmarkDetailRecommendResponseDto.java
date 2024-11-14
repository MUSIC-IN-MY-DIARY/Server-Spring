package com.diary.musicinmydiaryspring.bookmark.dto;

import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDetailRecommendResponseDto {
    @JsonProperty("bookmark_id")
    private Long id;

    @JsonProperty("chat")
    private ChatRecommendResponseDto chatRecommendResponseDto;

    @JsonProperty("song")
    private SongResponseDto songResponseDto;

    @JsonProperty("diary")
    private DiaryResponseDto diaryResponseDto;
}
