package com.diary.musicinmydiaryspring.chat.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="song_id", nullable = false)
    private Song song;

    @Column(unique = true, nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(unique = true, nullable = false)
    private String chatResponse;

    @Column
    private Boolean isLiked;

    @Builder
    public Chat(Song song, LocalDateTime createdAt, String chatResponse, Boolean isLiked){
        this.song = song;
        this.createdAt = createdAt;
        this.chatResponse = chatResponse;
        this.isLiked = isLiked;
    }
}
