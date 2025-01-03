package com.diary.musicinmydiaryspring.jwt.repository;

import com.diary.musicinmydiaryspring.jwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByMemberId(Long memberId);
    Optional<Token> findByRefreshToken(String refreshToken);
}
