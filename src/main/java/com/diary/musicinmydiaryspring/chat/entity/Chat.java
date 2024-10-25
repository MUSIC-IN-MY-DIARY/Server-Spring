package com.diary.musicinmydiaryspring.chat.entity;

import com.diary.musicinmydiaryspring.song.entity.Song;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String response;

    @Column
    private Boolean isSaved;

    @Builder
    public Chat(Song song, LocalDateTime createdAt, String response, Boolean isSaved){
        this.song = song;
        this.createdAt = createdAt;
        this.response = response;
        this.isSaved = isSaved;
    }
}
