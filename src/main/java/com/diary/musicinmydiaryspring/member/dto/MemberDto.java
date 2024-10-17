package com.diary.musicinmydiaryspring.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Data
@RequiredArgsConstructor
public class MemberDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("profile")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profile;

    @Builder
    public MemberDto(Long id, String email, String nickname, String profile){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
    }
}
