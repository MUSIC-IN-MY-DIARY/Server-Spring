package com.diary.musicinmydiaryspring.bookmark.repository;

import com.diary.musicinmydiaryspring.bookmark.entity.Bookmark;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByMemberAndChat(Member member, Chat chat);
}
