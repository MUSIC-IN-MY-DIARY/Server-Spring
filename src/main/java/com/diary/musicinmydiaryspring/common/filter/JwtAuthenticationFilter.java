package com.diary.musicinmydiaryspring.common.filter;

import com.diary.musicinmydiaryspring.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import static com.diary.musicinmydiaryspring.common.utils.RequestParser.*;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    private final String[] whiteListUris = new String[]{
            "/swagger-ui/**", "/v3/**", "/login", "/swagger-ui.html", "/swagger-resources/**", "/signup"
    };

    /**
     * 필터 처리 메서드 - 요청의 Authorization 헤더를 통해 JWT를 검증하고, SecurityContextHolder에 인증 객체를 설정
     * @param request  HTTP 요청
     * @param response HTTP 응답
     * @param filterChain 필터 체인
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
        if (isWhiteListed(request.getRequestURI())){
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(Authorization);

        if ( authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            String jwtToken = extractAccessToken(request);

            try{
                Authentication authentication = jwtProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e){
                sendJwtExceptionResponse(response, new RuntimeException("인증 실패"));
                return;
            }
        }

        String token = extractAccessToken(request);
        String email = jwtProvider.getClaims(token).getSubject();
        request.setAttribute("email", email);

        filterChain.doFilter(request, response);
    }

    /**
     * JWT 예외 처리 응답 메서드 - 인증 실패 시 응답을 생성
     * @param response HTTP 응답
     * @param e 발생한 런타임 예외
     * @throws IOException 입력/출력 예외 발생 시
     */
    private void sendJwtExceptionResponse(HttpServletResponse response, RuntimeException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String error = objectMapper.writeValueAsString(
                Collections.singletonMap("error", e.getMessage())
        );

        response.getWriter().write(error);
    }

    /**
     * 요청 URI가 화이트리스트에 포함되어 있는지 체크하는 메서드
     * @param uri 요청 URI
     * @return 화이트리스트에 포함 여부 (true/false)
     */
    private boolean isWhiteListed(String uri) {
        return PatternMatchUtils.simpleMatch(whiteListUris, uri);
    }
}