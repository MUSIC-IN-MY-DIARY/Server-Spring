package com.diary.musicinmydiaryspring;

import jakarta.persistence.*;

@Entity
@Table(name="member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String profile;
}
