package com.diary.musicinmydiaryspring.chat.dto.recommend;

import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ChatRecommendResponseDto{
    @JsonProperty("chat_response")
    private ChatResponseDto chatResponseDto;

    @JsonProperty("answer")
    private String recommendedSongs;
}
