package com.diary.musicinmydiaryspring.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ROLE_USER("USER");

    private final String value;
}
