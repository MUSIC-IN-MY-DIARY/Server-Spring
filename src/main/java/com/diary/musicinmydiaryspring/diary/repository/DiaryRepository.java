package com.diary.musicinmydiaryspring.diary.repository;

import com.diary.musicinmydiaryspring.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
