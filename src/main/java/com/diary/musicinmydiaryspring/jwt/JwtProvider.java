package com.diary.musicinmydiaryspring.jwt;

import com.diary.musicinmydiaryspring.member.service.CustomMemberDetailService;
import io.jsonwebtoken.Claims;
import com.diary.musicinmydiaryspring.jwt.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final CustomMemberDetailService customMemberDetailService;

    @Value("${jwt.token.secret-key}")
    private String signature;

    private byte[] secret;
    private Key key;

    /**
     * 종속성 주입이 완료된 후 실행되는 초기화 메서드
     * 비밀키 설정, JWT 서명을 위한 Key 객체 초기화
     */
    @PostConstruct
    public void setSecretKey(){
        secret = signature.getBytes();
        key = Keys.hmacShaKeyFor(secret);
    }

    /**
     * JWT 토큰 생성 메서드
     * 이메일과 만료 날짜를 기반으로 JWT 토큰 생성
     * @param email - JWT에 담을 사용자 이메일
     * @param expireDate - 토큰 만료 시간
     * @return 생성된 JWT 토큰 문자열
     */
    private String createToken(String email, Date expireDate){
        try{
            String jwt =  Jwts.builder()
                    .setSubject(email)
                    .setExpiration(expireDate)
                    .signWith(key)
                    .compact();

            log.info("발급된 jwt : "+jwt);
            return jwt;
        } catch (Exception e){
            log.debug("jwt 생성 실패: " + e.getMessage());
            throw e;
        }

    }

    /**
     * JWT 토큰으로부터 주체 정보를 가져오는 메서드
     * @param token - 주체 정보를 추출할 JWT 토큰
     * @return 추출된 주체(클레임) 정보
     */
    public Claims getClaims(String token){ // jwt에서 subject를 가져옴
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * JWT 토큰을 기반으로 인증 객체를 가져오는 메서드
     * 사용자의 이메일을 추출한 뒤, UserDetails를 통해 인증 정보 생성
     * @param token - 인증 정보를 추출할 JWT 토큰
     * @return 인증 객체 (Authentication)
     */
    public Authentication getAuthentication(String token) throws JsonParseException{
        String email = getClaims(token).getSubject();
        UserDetails userDetails = customMemberDetailService.loadUserByUsername(email);

        if (userDetails == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * 액세스 토큰과 리프레시 토큰 쌍을 생성하는 메서드
     * @param email - JWT에 담을 사용자 이메일
     * @return 생성된 Jwt 객체 (액세스 토큰 및 리프레시 토큰 포함)
     */
    public Jwt generateJwtPair(String email) {
        String accessToken = createToken(email, getExpireDateAccessToken());
        String refreshToken = createToken(email, getExpireDateRefreshToken());
        return new Jwt(accessToken, refreshToken);
    }

    /**
     * 액세스 토큰의 만료 날짜를 계산하여 반환하는 메서드
     * @return 액세스 토큰 만료 시간
     */
    private Date getExpireDateAccessToken() {
        long expireTimeMils = 1000L * 60 * 360;
        return new Date(System.currentTimeMillis() + expireTimeMils);
    }

    /**
     * 리프레시 토큰의 만료 날짜를 계산하여 반환하는 메서드
     * @return 리프레시 토큰 만료 시간
     */
    private Date getExpireDateRefreshToken() {
        long expireTimeMils = 1000L * 60 * 60 * 24 * 60;
        return new Date(System.currentTimeMillis() + expireTimeMils);
    }

    /**
     * 액세스 토큰을 재발급하는 메서드
     * @param email - JWT에 담을 사용자 이메일
     * @return 재발급된 액세스 토큰 문자열
     */
    public String reissueAccessToken(String email){
        return createToken(email, getExpireDateAccessToken());
    }

    /**
     * JWT 토큰이 유효한지 검증하는 메서드
     * @param token - 검증할 JWT 토큰
     * @param userDetails - 검증에 사용할 사용자 정보
     * @return 토큰의 유효 여부 (true/false)
     */
    public boolean validToken(String token, UserDetails userDetails){
        try{
            Claims claims = getClaims(token);
            String email = claims.getSubject();
            return (email.equals(userDetails.getUsername()) && !claims.getExpiration().before(new Date()));
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public String createAccessToken(String email) {
        return null;
    }
}