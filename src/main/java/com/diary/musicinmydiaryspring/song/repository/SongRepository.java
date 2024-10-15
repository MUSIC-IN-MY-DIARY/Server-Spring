package com.diary.musicinmydiaryspring.song.repository;

import com.diary.musicinmydiaryspring.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
