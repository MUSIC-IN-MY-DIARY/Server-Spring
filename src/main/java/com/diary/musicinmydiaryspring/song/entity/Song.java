package com.diary.musicinmydiaryspring.song.entity;

import com.diary.musicinmydiaryspring.chat.entity.Chat;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name="song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String songTitle;

    @Column(nullable = false)
    private String genre;


    public static Song createSongWithChat(Chat chat, String artist, String songTitle, String genre){
        return Song.builder()
                .chat(chat)
                .artist(artist)
                .songTitle(songTitle)
                .genre(genre)
                .build();
    }
}