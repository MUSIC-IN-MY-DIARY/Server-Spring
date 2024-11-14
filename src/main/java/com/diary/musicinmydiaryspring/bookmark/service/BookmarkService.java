package com.diary.musicinmydiaryspring.bookmark.service;

import com.diary.musicinmydiaryspring.bookmark.dto.BookmarkResposneDto;
import com.diary.musicinmydiaryspring.bookmark.entity.Bookmark;
import com.diary.musicinmydiaryspring.bookmark.repository.BookmarkRepository;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.chat.repository.ChatRepository;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;

    /**
     * 특정 Chat을 북마크로 등록/해제하는 메서드
     * @param chatId Chat 엔티티 아이디
     * @param email 로그인한 사용자 정보
     * return 북마크가 추가되면 true, 해제되면 false 반환
     * */
    @Transactional
    public BaseResponse<BookmarkResposneDto> updateBookmark(String email, Long chatId){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()-> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));

        Bookmark existingBookmark = bookmarkRepository.findByMemberAndChat(member, chat)
                .orElse(null);

        BookmarkResposneDto bookmarkResposneDto;
        if (existingBookmark != null){
            bookmarkRepository.delete(existingBookmark);
            bookmarkResposneDto = BookmarkResposneDto.builder().isBookmarked(false).build();
        } else{
            bookmarkRepository.save(Bookmark.createBookmark(chat, member, true));
            bookmarkResposneDto = BookmarkResposneDto.builder().isBookmarked(true).build();
        }
        return new BaseResponse<>(bookmarkResposneDto);
    }


    /**
     * 사용자가 북마크한 Chat봇의 작사 응답 보기
     * @param email 로그인한 사용자의 email
     * @param chatId 북마크된 chatId
     * */
    public BaseResponse<ChatLyricsResponseDto> getDetailBookmarkLyrics(String email, Long chatId) {
        Bookmark bookmark = bookmarkRepository.findBookmarkByChatId(chatId)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

        // 북마크된 chat 불러오기
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));

        return null;
    }
}
