package com.diary.musicinmydiaryspring.auth;

import com.diary.musicinmydiaryspring.member.dto.RegisterRequestDto;
import com.diary.musicinmydiaryspring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequestDto){
        memberService.register(registerRequestDto);
        return ResponseEntity.ok("회원가입 완");
    }
}
