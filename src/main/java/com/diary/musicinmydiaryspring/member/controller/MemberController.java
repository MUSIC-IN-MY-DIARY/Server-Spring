package com.diary.musicinmydiaryspring.member.controller;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.member.dto.MemberInfoResponseDto;
import com.diary.musicinmydiaryspring.member.dto.MemberResponseDto;
import com.diary.musicinmydiaryspring.member.dto.MemberRequestDto;

import com.diary.musicinmydiaryspring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public BaseResponse<MemberResponseDto> signup(@Validated @RequestBody MemberRequestDto memberRequestDto){
        return memberService.signup(memberRequestDto);
    }

    @GetMapping("/verify")
    public BaseResponse<MemberResponseDto> verifyMember(
            Principal principal
    ){
        String email = principal.getName();
        return memberService.verifyMember(email);
    }

    @GetMapping("/info")
    public BaseResponse<MemberInfoResponseDto> provideMemberInfo(
            Principal principal
    ){
        String email = principal.getName();
        return memberService.provideMemberInfo(email);
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(
            Principal principal
    ){
        String email = principal.getName();
        return memberService.logout(email);
    }

}
