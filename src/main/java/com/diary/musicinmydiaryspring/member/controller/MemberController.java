package com.diary.musicinmydiaryspring.member.controller;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.member.dto.MemberDto;
import com.diary.musicinmydiaryspring.member.dto.SignupRequestDto;

import com.diary.musicinmydiaryspring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse<MemberDto>> signup(@RequestBody SignupRequestDto signupRequestDto){
        BaseResponse<MemberDto> response = memberService.signup(signupRequestDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
