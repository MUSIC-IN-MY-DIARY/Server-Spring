package com.diary.musicinmydiaryspring.chat.dto.recommend;

import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecommendResponseDto{
    @JsonProperty("chat_response")
    private ChatResponseDto chatResponseDto;

    @JsonProperty("answer")
    private String recommendedSongs;

    @JsonProperty("song_id")
    private List<Long> songId;

    @JsonProperty("image_id")
    private List<Long> imageId;
}
