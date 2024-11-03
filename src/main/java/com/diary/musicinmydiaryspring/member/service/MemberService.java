package com.diary.musicinmydiaryspring.member.service;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.member.dto.MemberSignupResponseDto;
import com.diary.musicinmydiaryspring.member.dto.MemberSignupRequestDto;
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
    public BaseResponse<MemberSignupResponseDto> signup(MemberSignupRequestDto memberSignupRequestDto){

        if (memberRepository.existsByEmail(memberSignupRequestDto.getUsername())){
            throw new CustomRuntimeException(BaseResponseStatus.ALREADY_EXIST_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(memberSignupRequestDto.getPassword());

        Member member = Member.builder()
                .email(memberSignupRequestDto.getUsername())
                .password(encodedPassword)
                .nickname(memberSignupRequestDto.getNickname())
                .build();

        memberRepository.save(member);

        return new BaseResponse<>(MemberSignupResponseDto.builder()
                .id(member.getId())
                .email(member.getUsername())
                .nickname(member.getNickname())
                .build());
    }
}
