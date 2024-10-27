package com.diary.musicinmydiaryspring.common.utils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParser {

    public static final String EMAIL = "email";
    public static final String Authorization = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * HTTP 요청에서 Authorization 헤더에 담긴 JWT 액세스 토큰을 추출하는 메서드
     * @param request HTTP 요청 객체
     * @return 추출된 JWT 토큰 문자열, Authorization 헤더가 없거나 유효하지 않으면 null 반환
     */
    public static String extractAccessToken(HttpServletRequest request){
        String authoriztionHeader = request.getHeader(Authorization);
        if (authoriztionHeader != null && authoriztionHeader.startsWith(TOKEN_PREFIX)){
            return authoriztionHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }


    /**
     * JWT의 Claims 객체로부터 이메일 정보를 추출하는 메서드
     * @param claims JWT Claims 객체
     * @return JWT 클레임에서 추출된 이메일 (subject로 저장된 값)
     */
    public static String extractEmailFromToken(Claims claims){
        return String.valueOf(claims.getSubject());
    }
}
