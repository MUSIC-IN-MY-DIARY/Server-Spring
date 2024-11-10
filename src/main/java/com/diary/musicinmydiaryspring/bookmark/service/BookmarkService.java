package com.diary.musicinmydiaryspring.bookmark.service;

import com.diary.musicinmydiaryspring.bookmark.dto.BookmarkDetailResponseDto;
import com.diary.musicinmydiaryspring.bookmark.entity.Bookmark;
import com.diary.musicinmydiaryspring.bookmark.repository.BookmarkRepository;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    /**
     * Chat 엔티티를 북마크로 등록/해제하는 메서드
     * @param chat Chat 엔티티
     * */
    @Transactional
    public void updateBookmark(Chat chat){

        if (chat == null){
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT);
        }

        Boolean exists = bookmarkRepository.existsByChat(chat);

        if (exists){ // 이미 등록되어 있다면 북마크에서 해제
            Bookmark bookmark = bookmarkRepository.findBookmarkByChatId(chat.getId())
                    .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK));

            bookmarkRepository.delete(bookmark);
        }else{ // 북마크에 없었다면 북마크로 등록
            Bookmark bookmark = new Bookmark();
            bookmark.setChat(chat);
            bookmarkRepository.save(bookmark);
        }

    }


}
