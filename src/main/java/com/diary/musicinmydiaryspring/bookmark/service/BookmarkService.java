package com.diary.musicinmydiaryspring.bookmark.service;

import com.diary.musicinmydiaryspring.bookmark.dto.BookmarkDetailLyricsResponseDto;
import com.diary.musicinmydiaryspring.bookmark.dto.BookmarkDetailRecommendResponseDto;
import com.diary.musicinmydiaryspring.bookmark.dto.BookmarkResponseDto;
import com.diary.musicinmydiaryspring.bookmark.entity.Bookmark;
import com.diary.musicinmydiaryspring.bookmark.repository.BookmarkRepository;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.chat.repository.ChatRepository;
import com.diary.musicinmydiaryspring.chat.service.ChatService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import com.diary.musicinmydiaryspring.song.entity.Song;
import com.diary.musicinmydiaryspring.song.repository.SongRepository;
import com.diary.musicinmydiaryspring.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;
    private final ChatService chatService;
    private final SongRepository songRepository;
    private final SongService songService;

    /**
     * 특정 Chat을 북마크로 등록/해제하는 메서드
     * @param chatId Chat 엔티티 아이디
     * @param email 로그인한 사용자 정보
     * return 북마크가 추가되면 true, 해제되면 false 반환
     * */
    @Transactional
    public BaseResponse<BookmarkResponseDto> updateBookmark(String email, Long chatId){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()-> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));

        Bookmark existingBookmark = bookmarkRepository.findByMemberAndChat(member, chat)
                .orElse(null);

        BookmarkResponseDto bookmarkResponseDto;
        if (existingBookmark != null){
            bookmarkRepository.delete(existingBookmark);
            bookmarkResponseDto = BookmarkResponseDto.builder().isBookmarked(false).build();
        } else{
            bookmarkRepository.save(Bookmark.createBookmark(chat, member, true));
            bookmarkResponseDto = BookmarkResponseDto.builder().isBookmarked(true).build();
        }
        return new BaseResponse<>(bookmarkResponseDto);
    }


    /**
     * 북마크 된 작사 응답 디테일 조회
     * */
    public BaseResponse<BookmarkDetailLyricsResponseDto> getDetailBookmarkLyrics(String email, Long chatId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()-> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));

        Bookmark bookmark = bookmarkRepository.findByMemberAndChat(member, chat)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK));

        ChatResponseDto chatResponseDto = chatService.createChatResponseDto(chat);

        if (bookmark.getIsBookmark()){

            // 파라미터로 받은 chatId에 해당하는 chat을 조회해야함.ChatLyricsResponseDto에 넣어야 함.
            ChatLyricsResponseDto chatLyricsResponseDto = ChatLyricsResponseDto.builder()
                    .chatResponseDto(chatResponseDto)
                    .generatedLyrics(chat.getLyrics())
                    .build();

            // 해당 Chat이 참조하는 Diary를 조회해야함. DiaryResponseDto에 넣어야함.
            DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                    .id(chat.getDiary().getId())
                    .nickName(chat.getDiary().getMember().getNickname())
                    .createdAt(chat.getDiary().getCreatedAt())
                    .content(chat.getDiary().getContent())
                    .build();

            BookmarkDetailLyricsResponseDto bookmarkDetailLyricsResponseDto = BookmarkDetailLyricsResponseDto.builder()
                    .id(bookmark.getId())
                    .chatLyricsResponseDto(chatLyricsResponseDto)
                    .diaryResponseDto(diaryResponseDto)
                    .build();

            return new BaseResponse<>(bookmarkDetailLyricsResponseDto);
        } else {
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK);
        }

    }

    public BaseResponse<BookmarkDetailRecommendResponseDto> getDetailBookmarkRecommend(String email, Long songId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_SONG));

        Chat chat = chatRepository.findById(song.getChat().getId())
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));


        Bookmark bookmark = bookmarkRepository.findByMemberAndChat(member, chat)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK));

        ChatResponseDto chatResponseDto = chatService.createChatResponseDto(chat);

        List<Long> songIds = songService.findSongIdsByChatId(chat.getId());

        if (bookmark.getIsBookmark()) {
            // 파라미터로 받은 chatId에 해당하는 chat을 조회해야함.ChatLyricsResponseDto에 넣어야 함.
            ChatRecommendResponseDto chatRecommendResponseDto = ChatRecommendResponseDto.builder()
                    .chatResponseDto(chatResponseDto)
                    .songId(songIds)
                    .build();

            // 해당 Chat이 참조하는 Diary를 조회해야함. DiaryResponseDto에 넣어야함.
            DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                    .id(chat.getDiary().getId())
                    .nickName(chat.getDiary().getMember().getNickname())
                    .createdAt(chat.getDiary().getCreatedAt())
                    .content(chat.getDiary().getContent())
                    .build();

            BookmarkDetailRecommendResponseDto bookmarkDetailLyricsResponseDto = BookmarkDetailRecommendResponseDto.builder()
                    .id(bookmark.getId())
                    .chatRecommendResponseDto(chatRecommendResponseDto)
                    .diaryResponseDto(diaryResponseDto)
                    .build();

            return new BaseResponse<>(bookmarkDetailLyricsResponseDto);
        } else {
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK);
        }
    }
}
