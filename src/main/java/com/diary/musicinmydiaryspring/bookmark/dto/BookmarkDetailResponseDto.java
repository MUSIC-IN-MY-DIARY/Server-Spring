package com.diary.musicinmydiaryspring.bookmark.dto;

import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkDetailResponseDto {
    @JsonProperty("chat_response_lyrics")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ChatLyricsResponseDto chatLyricsResponseDto;

    @JsonProperty("chat_response_recommend")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ChatRecommendResponseDto chatRecommendResponseDto;

    @JsonProperty("diary")
    private DiaryResponseDto diary;
}
