package com.diary.musicinmydiaryspring.bookmark.entity;

import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.song.entity.Song;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;


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

    @JoinColumn(name="member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "is_bookmark")
    private Boolean isBookmark;

    public static Bookmark createBookmark(Chat chat, Member member, Boolean isBookmark){
        return Bookmark.builder()
                .chat(chat)
                .member(member)
                .isBookmark(isBookmark)
                .build();
    }

}
