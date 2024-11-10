package com.diary.musicinmydiaryspring.member.service;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.member.dto.MemberResponseDto;
import com.diary.musicinmydiaryspring.member.dto.MemberRequestDto;
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
    public BaseResponse<MemberResponseDto> signup(MemberRequestDto memberRequestDto){

        if (memberRepository.existsByEmail(memberRequestDto.getUsername())){
            throw new CustomRuntimeException(BaseResponseStatus.ALREADY_EXIST_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        Member member = Member.builder()
                .email(memberRequestDto.getUsername())
                .password(encodedPassword)
                .nickname(memberRequestDto.getNickname())
                .build();

        memberRepository.save(member);

        return new BaseResponse<>(MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getUsername())
                .nickname(member.getNickname())
                .build());
    }
}
