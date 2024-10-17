package com.diary.musicinmydiaryspring.filter;

import com.diary.musicinmydiaryspring.jwt.service.JwtService;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.service.MemberService;
import com.diary.musicinmydiaryspring.jwt.Jwt;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MemberService memberService;

    /**
     * Login 시도 메서드
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException {
            String email = (obtainUsername(request) != null) ? obtainUsername(request) : "";
            String password = (obtainPassword(request) != null) ? obtainPassword(request) : "";

            System.out.println(request);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,password,null);
                return authenticationManager.authenticate(authToken);
    }

    /**
     * Login 성공(인증 성공) 시 메서드
     * authResult : 인증 성공 후 만들어지는 인증 객체
     * */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException
    {
        Member member = (Member) authResult.getPrincipal();
//        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

        Jwt token = jwtService.createTokens(member.getId());
        addJwtToCookie(response, token.getAccessToken(), "accessToken");

        sendMemberLoginResponse(response, HttpStatus.OK);
    }

    /**
     * Login 실패 시(인증 실패) 메서드
     * failed : 인증 실패 후 만들어지는 인증 실패 객체
     * */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
    {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("인증 실패 : " + failed.getMessage());
    }

    /**
     * Login 성공/실패 시 메세지 보내는 메서드
     * */
    private void sendMemberLoginResponse(HttpServletResponse response, HttpStatus status) throws IOException{
        Map<String, String> messageMap = new HashMap<>();
        response.setContentType("application/json;charet=UTF-8");
        response.setStatus(status.value());
        PrintWriter out = response.getWriter();
        out.print(new ObjectMapper().writeValueAsString(messageMap));
        out.flush();
    }

    /**
     * 쿠키 굽는 메서드
     * */
    public void addJwtToCookie(HttpServletResponse response, String jwtToken, String cookieName){
        Cookie cookie = new Cookie(cookieName, jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*120);
        response.addCookie(cookie);
    }
}
