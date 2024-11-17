package com.diary.musicinmydiaryspring.member.service;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.jwt.repository.TokenRepository;
import com.diary.musicinmydiaryspring.member.dto.MemberInfoResponseDto;
import com.diary.musicinmydiaryspring.member.dto.MemberResponseDto;
import com.diary.musicinmydiaryspring.member.dto.MemberRequestDto;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

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

    public BaseResponse<MemberInfoResponseDto> provideMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

        return new BaseResponse<>(MemberInfoResponseDto.builder()
                .nickname(member.getNickname())
                .build());
    }

    @Transactional
    public BaseResponse<Void> logout(String email) {
        try {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

            tokenRepository.findByMemberId(member.getId())
                    .ifPresent(token -> tokenRepository.delete(token));

            Cookie expiredAccessToken = createExpiredCookie("accessToken");

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletResponse response = attr.getResponse();
            if (response != null){
                response.addCookie(expiredAccessToken);
            }
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch (Exception e){
            log.error("Logout failed for email : {}", email, e);
            throw new CustomRuntimeException(BaseResponseStatus.INTERNAL_SERVER_ERROR);

        }
    }

    private Cookie createExpiredCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        return cookie;
    }
}
