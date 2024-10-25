package com.diary.musicinmydiaryspring.chat.repository;

import com.diary.musicinmydiaryspring.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
