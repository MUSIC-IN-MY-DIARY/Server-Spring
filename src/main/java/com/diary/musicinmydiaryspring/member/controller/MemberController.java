package com.diary.musicinmydiaryspring.member.controller;

import com.diary.musicinmydiaryspring.member.dto.SignupDto;
import com.diary.musicinmydiaryspring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto){
        memberService.register(signupDto);
        return ResponseEntity.ok("회원가입 완");
    }
}
