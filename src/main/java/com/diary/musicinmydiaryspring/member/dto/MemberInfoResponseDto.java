package com.diary.musicinmydiaryspring.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponseDto {
    @JsonProperty("nickname")
    private String nickname;
}
