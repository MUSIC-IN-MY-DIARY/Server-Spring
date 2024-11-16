package com.diary.musicinmydiaryspring.bookmark.repository;

import com.diary.musicinmydiaryspring.bookmark.entity.Bookmark;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByMemberAndChat(Member member, Chat chat);
    Page<Bookmark> findAllByMemberAndIsBookmarkTrueAndChat_LyricsIsNotNull(Member member, Pageable pageable);

    @Query("SELECT b FROM Bookmark b " +
            "JOIN b.chat c " +
            "WHERE b.member = :member " +
            "AND b.isBookmark = true " +
            "AND EXISTS (SELECT s FROM Song s WHERE s.chat = c)")
    Page<Bookmark> findAllByMemberAndIsBookmarkTrueAndChat_RecommendIsNotNull(@Param("member") Member member, Pageable pageable);
}
