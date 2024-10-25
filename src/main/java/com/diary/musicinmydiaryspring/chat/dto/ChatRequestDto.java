package com.diary.musicinmydiaryspring.chat.dto;

import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatRequestDto {
    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("diary_content")
    private String diaryContent;

    public ChatRequestDto(Long memberId, String diaryContent){
        this.memberId = memberId;
        this.diaryContent = diaryContent;
    }
}
