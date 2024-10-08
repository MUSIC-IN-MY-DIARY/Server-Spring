package com.diary.musicinmydiaryspring.member.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String profile;


}
