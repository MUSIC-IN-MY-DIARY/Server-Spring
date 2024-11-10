package com.diary.musicinmydiaryspring.bookmark.entity;

import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name="bookmark")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "chat_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Chat chat;
}
