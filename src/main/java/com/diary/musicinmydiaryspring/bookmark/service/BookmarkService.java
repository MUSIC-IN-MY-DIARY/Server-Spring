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

    @Transactional
    public void addBookmark(Chat chat){

        if (chat == null){
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT);
        }

        Boolean exists = bookmarkRepository.existsByChat(chat);

        if (exists){
            throw new CustomRuntimeException(BaseResponseStatus.ALREADY_ADD_BOOKMARK);
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setChat(chat);
        bookmarkRepository.save(bookmark);
    }


    @Transactional
    public BaseResponse<BookmarkDetailResponseDto> getDetailBookmark(Long chatId) {

        Bookmark bookmark = bookmarkRepository.findBookmarkByChatId(chatId)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK));

        Chat chat = bookmark.getChat();
        if (chat == null){
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT);
        }

        Diary diary = chat.getDiary();
        if (diary == null){
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_DIARY);
        }

        ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                .id(chat.getId())
                .createdAt(chat.getCreatedAt() != null ? chat.getCreatedAt() : LocalDateTime.now())
                .chatResponse(chat.getChatResponse() != null ? chat.getChatResponse() : "")
                .build();

        DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                .id(diary.getId())
                .createdAt(diary.getCreatedAt() != null ? diary.getCreatedAt() : LocalDateTime.now())
                .updatedAt(diary.getUpdatedAt() != null ? diary.getUpdatedAt() : LocalDateTime.now())
                .content(diary.getContent() != null ? diary.getContent() : "")
                .build();

        return new BaseResponse<>(BookmarkDetailResponseDto.builder()
                .chat(chatResponseDto)
                .diary(diaryResponseDto)
                .build());

    }

}
