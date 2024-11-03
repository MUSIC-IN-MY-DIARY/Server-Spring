package com.diary.musicinmydiaryspring.jwt.entity;

import com.diary.musicinmydiaryspring.jwt.Jwt;
import com.diary.musicinmydiaryspring.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
    @SequenceGenerator(name = "token_generator", sequenceName = "token_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(nullable = false)
    private String refreshToken;

    @Column
    private LocalDateTime expiration;

    @Builder
    public static Token of(Member member, Jwt jwt){
        return Token.builder()
                .member(member)
                .tokenType(TokenType.BEARER)
                .refreshToken(jwt.getRefreshToken())
                .expiration(jwt.getRefreshTokenExpriation())
                .build();
    }

    public enum TokenType {
        BEARER
    }
}
