package com.diary.musicinmydiaryspring.chat.entity;

import com.diary.musicinmydiaryspring.song.entity.Song;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(unique = true, nullable = false)
    private String response;

    @Builder
    public Chat(Song song, String response){
        this.song = song;
        this.response = response;
    }
}
