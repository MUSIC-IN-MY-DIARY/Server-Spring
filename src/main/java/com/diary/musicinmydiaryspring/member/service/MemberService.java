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

        Member member = Member.createMember(
                memberRequestDto.getUsername(),
                memberRequestDto.getPassword(),
                memberRequestDto.getNickname(),
                passwordEncoder
        );

        memberRepository.save(member);

        return new BaseResponse<>(MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getUsername())
                .nickname(member.getNickname())
                .build());
    }

    /**
     * 회원 정보 검증 서비스
     *
     * @param email 검증할 회원 이메일
     * @return 회원 검증 결과 응답
     * @throws CustomRuntimeException 회원이 존재하지 않을 경우 예외 발생
     */
    public BaseResponse<MemberResponseDto> verifyMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

        return new BaseResponse<>(MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build());
    }
}
