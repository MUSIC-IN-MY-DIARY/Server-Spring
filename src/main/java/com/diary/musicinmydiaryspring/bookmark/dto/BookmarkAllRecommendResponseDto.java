package com.diary.musicinmydiaryspring.bookmark.dto;

import com.diary.musicinmydiaryspring.common.dto.PageDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookmarkAllRecommendResponseDto {
    private PageDto pageInfo;
    private List<BookmarkRecommendDto> bookmarks;

    @Getter
    @Builder
    public static class BookmarkRecommendDto{
        private List<Long> songIds;
        private Long diaryId;
        private String diaryContent;
    }
}
