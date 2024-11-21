package com.diary.musicinmydiaryspring.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageDto {
    private int pageSize;
    private int currentPage;
    private long totalCount;
    private boolean isLastPage;
} 