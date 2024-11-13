package com.diary.musicinmydiaryspring.chat.dto.generate;

import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatLyricsResponseDto{
    @JsonProperty("chat_response")
    private ChatResponseDto chatResponseDto;

    @JsonProperty("answer")
    private String generatedLyrics;
}
