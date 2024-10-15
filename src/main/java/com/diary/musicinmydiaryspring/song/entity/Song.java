package com.diary.musicinmydiaryspring.song.entity;

import jakarta.persistence.*;
import lombok.Data;

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
}
