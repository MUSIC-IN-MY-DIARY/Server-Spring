package com.diary.musicinmydiaryspring.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Data
@RequiredArgsConstructor
public class MemberSignupResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("nickname")
    private String nickname;


    @Builder
    public MemberSignupResponseDto(Long id, String email, String nickname){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}
