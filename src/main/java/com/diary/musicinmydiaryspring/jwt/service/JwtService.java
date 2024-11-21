package com.diary.musicinmydiaryspring.jwt.service;

import com.diary.musicinmydiaryspring.jwt.JwtProvider;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import com.diary.musicinmydiaryspring.jwt.entity.Token;
import com.diary.musicinmydiaryspring.jwt.repository.TokenRepository;
import com.diary.musicinmydiaryspring.jwt.dto.ReissueTokenRequestDto;
import com.diary.musicinmydiaryspring.jwt.dto.ReissueTokenResponseDto;
import com.diary.musicinmydiaryspring.jwt.Jwt;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    /**
     * 로그인 - memberId를 받아 ToeknPair 생성 후 db에 저장
     * @param memberId 사용자 ID
     * @return Jwt 토큰 쌍 (액세스 토큰 및 리프레시 토큰)
     */
    @Transactional
    public Jwt createTokens(Long memberId){
        validateMember(memberId);

        String email = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다 : " + memberId))
                .getEmail();

        Jwt jwt = jwtProvider.generateJwtPair(email);

        Token token = Token.of(memberRepository.findById(memberId).orElseThrow(), jwt);
        tokenRepository.findByMemberId(memberId).ifPresent(existingToken -> tokenRepository.delete(existingToken));
        tokenRepository.save(token);
        return jwt;
    }

    /**
     * Member 존재 여부 검증 메서드
     * @param memberId
     * */
    private void validateMember(Long memberId){
        memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다 : "+ memberId));
    }

    /**
     * 토큰 재발급 요청 처리 메서드
     * @param reissueTokenRequestDto
     * return ReissueTokenResponse
     */
    public ReissueTokenResponseDto reissueTokenResponse(ReissueTokenRequestDto reissueTokenRequestDto){
        Token token = tokenRepository.findByRefreshToken(reissueTokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new JwtException("Refresh Token이 없습니다."));

        String reissueAccessToken = jwtProvider.reissueAccessToken(token.getMember().getEmail());

        return ReissueTokenResponseDto.of(token, reissueAccessToken);
    }
}