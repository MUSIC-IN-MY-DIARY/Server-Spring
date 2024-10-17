package com.diary.musicinmydiaryspring.diary.entity;

import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.song.entity.Song;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "diary")
public class Diary {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String content;

    @Column
    private String isSaved;

    @PrePersist
    public void prePersist(){
        this.createdAt = (this.createdAt == null) ? LocalDateTime.now() : this.createdAt;
    }
}
