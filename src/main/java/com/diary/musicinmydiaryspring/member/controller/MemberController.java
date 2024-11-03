package com.diary.musicinmydiaryspring.member.controller;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.member.dto.MemberSignupResponseDto;
import com.diary.musicinmydiaryspring.member.dto.MemberSignupRequestDto;

import com.diary.musicinmydiaryspring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public BaseResponse<MemberSignupResponseDto> signup(@Validated @RequestBody MemberSignupRequestDto memberSignupRequestDto){
        return memberService.signup(memberSignupRequestDto);
    }
}
