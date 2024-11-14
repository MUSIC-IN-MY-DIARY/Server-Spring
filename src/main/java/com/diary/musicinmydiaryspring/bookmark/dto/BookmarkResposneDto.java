package com.diary.musicinmydiaryspring.bookmark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class BookmarkResposneDto {
    @JsonProperty("is_bookmarked")
    private Boolean isBookmarked;
}
