package com.diary.musicinmydiaryspring.bookmark.dto;

import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkDetailResponseDto {
    private ChatResponseDto chat;
    private DiaryResponseDto diary;
}
