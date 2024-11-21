package com.diary.musicinmydiaryspring.bookmark.dto;

import com.diary.musicinmydiaryspring.common.dto.PageDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookmarkAllLyricsResponseDto {
    private PageDto pageInfo;
    private List<BookmarkLyricsDto> bookmarks;

    @Getter
    @Builder
    public static class BookmarkLyricsDto {
        private Long diaryId;
        private Long chatId;
        private String diaryContent;
        private String generatedLyrics;
    }
} 