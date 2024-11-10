package com.diary.musicinmydiaryspring.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDetailRequestDto {
    @JsonProperty("chat_id")
    private Long chatId;
}
