package com.diary.musicinmydiaryspring.chat.entity;

import com.diary.musicinmydiaryspring.diary.entity.Diary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat")
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id", nullable = false)
    private Diary diary;

    @Column(unique = true, nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String lyrics;


    public static Chat createChat(Diary diary) {
        return Chat.builder()
                .diary(diary)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Chat createChatWithLyrics(Diary diary, String lyrics) {
        return Chat.builder()
                .diary(diary)
                .createdAt(LocalDateTime.now())
                .lyrics(lyrics)
                .build();
    }


}