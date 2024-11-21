package com.diary.musicinmydiaryspring.bookmark.dto;

import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDetailLyricsResponseDto {
    @JsonProperty("bookmark_id")
    private Long id;

    @JsonProperty("chat")
    private ChatLyricsResponseDto chatLyricsResponseDto;

    @JsonProperty("diary")
    private DiaryResponseDto diaryResponseDto;
}
