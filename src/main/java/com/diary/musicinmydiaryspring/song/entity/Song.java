package com.diary.musicinmydiaryspring.song.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Data
@Table(name="song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String albumTitle;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String songTitle;

    @Column(nullable = false)
    private String lyrics;

    @Column(nullable = false)
    private String genre;

    public Song(String albumTitle, String artist, String songTitle, String lyrics, String genre){
        this.albumTitle = albumTitle;
        this.artist = artist;
        this.songTitle = songTitle;
        this.lyrics = lyrics;
        this.genre = genre;
    }
}
