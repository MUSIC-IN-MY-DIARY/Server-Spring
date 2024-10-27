package com.diary.musicinmydiaryspring.diary.repository;

import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<List<Diary>> findAllByMemberId(Long memberId);
}
