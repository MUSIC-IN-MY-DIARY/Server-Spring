package com.diary.musicinmydiaryspring.member.service;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.member.dto.MemberDto;
import com.diary.musicinmydiaryspring.member.dto.SignupRequestDto;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BaseResponse<MemberDto> signup(SignupRequestDto signupRequestDto){

        if (memberRepository.existsByEmail(signupRequestDto.getUsername())){
            return new BaseResponse<>(BaseResponseStatus.ALREADY_EXIST_EMAIL);
        }

        if (!signupRequestDto.isPasswordConfirmed()){
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST_DIARY_INPUT);
        }

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        Member member = Member.builder()
                .email(signupRequestDto.getUsername())
                .password(encodedPassword)
                .nickname(signupRequestDto.getNickname())
                .build();

        memberRepository.save(member);

        return new BaseResponse<>(MemberDto.builder()
                .email(member.getUsername())
                .nickname(member.getNickname())
                .build());
    }
}
