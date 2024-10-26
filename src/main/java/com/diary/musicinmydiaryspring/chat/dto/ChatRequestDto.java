package com.diary.musicinmydiaryspring.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ChatRequestDto {
    @JsonProperty("chat_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long chatId;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("diary_content")
    private String diaryContent;

    public ChatRequestDto(Long memberId, String diaryContent){
        this.memberId = memberId;
        this.diaryContent = diaryContent;
    }
}
