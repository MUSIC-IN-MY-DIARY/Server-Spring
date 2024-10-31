package com.diary.musicinmydiaryspring.chat.entity;

import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.song.entity.Song;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "chat")
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id", nullable = false)
    private Diary diary;

    @Column(unique = true, nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(unique = true, nullable = false)
    private String chatResponse;

    @Column
    private Boolean isLiked;

    @Builder
    public Chat(LocalDateTime createdAt, String chatResponse, Boolean isLiked){
        this.createdAt = createdAt;
        this.chatResponse = chatResponse;
        this.isLiked = isLiked;
    }
}
