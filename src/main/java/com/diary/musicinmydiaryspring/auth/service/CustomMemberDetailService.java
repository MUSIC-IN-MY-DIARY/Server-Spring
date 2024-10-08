package com.diary.musicinmydiaryspring.auth.service;

import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member =  memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일입니다 : " + email));
        return member;
    }

    public Member findMemberByMemberId(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->new EntityNotFoundException("존재하지 않는 회원아이디입니다 : "+memberId));
        return member;
    }
}
