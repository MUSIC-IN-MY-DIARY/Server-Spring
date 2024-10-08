package com.diary.musicinmydiaryspring.member.service;

import com.diary.musicinmydiaryspring.member.dto.RegisterRequestDto;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(RegisterRequestDto request){

        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodePassword = passwordEncoder.encode(request.getPassword());

        Member member = new Member(request.getUsername(), encodePassword, request.getNickname(), request.getProfile());
        return memberRepository.save(member);
    }
}
