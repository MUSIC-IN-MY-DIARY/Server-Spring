package com.diary.musicinmydiaryspring.bookmark.service;

import com.diary.musicinmydiaryspring.bookmark.dto.*;
import com.diary.musicinmydiaryspring.bookmark.entity.Bookmark;
import com.diary.musicinmydiaryspring.bookmark.repository.BookmarkRepository;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.chat.repository.ChatRepository;
import com.diary.musicinmydiaryspring.chat.service.ChatService;
import com.diary.musicinmydiaryspring.common.dto.PageDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
     * 특정 채팅에 대해 북마크 상태 업데이트
     *
     * @param email 북마크를 업데이트할 회원의 이메일
     * @param chatId 북마크로 추가하거나 해제할 채팅의 ID
     * @return 업데이트된 북마크 상태가 포함된 응답
     * @throws CustomRuntimeException 회원이나 채팅을 찾을 수 없을 때 발생
     */
    @Transactional
    public BaseResponse<BookmarkResponseDto> updateBookmark(String email, Long chatId){
        Member member = getMemberByEmail(email);
        Chat chat = getChatById(chatId);

        Bookmark existingBookmark = bookmarkRepository.findByMemberAndChat(member, chat).orElse(null);
        BookmarkResponseDto bookmarkResponseDto = (existingBookmark != null) ? removeBookmark(existingBookmark) : addBookmark(chat, member);

        return new BaseResponse<>(bookmarkResponseDto);
    }

    /**
     * 새 북마크 추가
     *
     * @param chat 북마크로 추가할 채팅
     * @param member 북마크를 추가하는 회원
     * @return 북마크가 추가되었음을 나타내는 응답
     */
    private BookmarkResponseDto addBookmark(Chat chat, Member member) {
        bookmarkRepository.save(Bookmark.createBookmark(chat, member, true));
        return BookmarkResponseDto.builder().isBookmarked(true).build();
    }

    /**
     * 기존 북마크 제거
     *
     * @param existingBookmark 제거할 북마크
     * @return 북마크가 제거되었음을 나타내는 응답
     */
    private BookmarkResponseDto removeBookmark(Bookmark existingBookmark) {
        bookmarkRepository.delete(existingBookmark);
        return BookmarkResponseDto.builder().isBookmarked(false).build();
    }

    /**
     * 북마크된 채팅의 작사 상세 정보 조회
     *
     * @param email 작사 정보를 요청하는 회원의 이메일
     * @param chatId 북마크된 채팅의 ID
     * @return 북마크된 채팅의 상세 작사 정보가 포함된 응답
     * @throws CustomRuntimeException 회원, 채팅 또는 북마크를 찾을 수 없을 때 발생
     */
    public BaseResponse<BookmarkDetailLyricsResponseDto> getDetailBookmarkLyrics(String email, Long chatId) {
        Member member = getMemberByEmail(email);
        Chat chat = getChatById(chatId);

        Bookmark bookmark = bookmarkRepository.findByMemberAndChat(member, chat)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK));


        if (!bookmark.getIsBookmark()){
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK);
        }

        ChatLyricsResponseDto chatLyricsResponseDto = createChatLyricsResponseDto(chat);
        DiaryResponseDto diaryResponseDto = createDiaryResponse(chat);

        BookmarkDetailLyricsResponseDto bookmarkDetailLyricsResponseDto = BookmarkDetailLyricsResponseDto.builder()
                    .id(bookmark.getId())
                    .chatLyricsResponseDto(chatLyricsResponseDto)
                    .diaryResponseDto(diaryResponseDto)
                    .build();

        return new BaseResponse<>(bookmarkDetailLyricsResponseDto);
    }


    /**
     * 북마크된 채팅의 노래 추천 상세 정보 조회
     *
     * @param email 추천 정보를 요청하는 회원의 이메일
     * @param songId 북마크된 노래가 연관된 채팅의 ID
     * @return 북마크된 노래의 추천 정보가 포함된 응답
     * @throws CustomRuntimeException 회원, 노래, 채팅 또는 북마크를 찾을 수 없을 때 발생
     */
    public BaseResponse<BookmarkDetailRecommendResponseDto> getDetailBookmarkRecommend(String email, Long songId) {
        Member member = getMemberByEmail(email);
        Song song = getSongById(songId);
        Chat chat = getChatById(song.getChat().getId());

        Bookmark bookmark = bookmarkRepository.findByMemberAndChat(member, chat)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK));

        if (!bookmark.getIsBookmark()) {
            throw new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_BOOKMARK);

        }
        ChatRecommendResponseDto chatRecommendResponseDto = createChatRecommendResponseDto(chat);
        DiaryResponseDto diaryResponseDto = createDiaryResponse(chat);

        BookmarkDetailRecommendResponseDto bookmarkDetailLyricsResponseDto = BookmarkDetailRecommendResponseDto.builder()
                    .id(bookmark.getId())
                    .chatRecommendResponseDto(chatRecommendResponseDto)
                    .diaryResponseDto(diaryResponseDto)
                    .build();

        return new BaseResponse<>(bookmarkDetailLyricsResponseDto);
    }

    private ChatRecommendResponseDto createChatRecommendResponseDto(Chat chat) {
        ChatResponseDto chatResponseDto = chatService.createChatResponseDto(chat);
        List<Long> songIds = songService.findSongIdsByChatId(chat.getId());
        return ChatRecommendResponseDto.builder()
                .chatResponseDto(chatResponseDto)
                .songId(songIds)
                .build();
    }

    private Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(()-> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));
    }

    private Song getSongById(Long songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_SONG));
    }

    private DiaryResponseDto createDiaryResponse(Chat chat) {
        return DiaryResponseDto.builder()
                .id(chat.getDiary().getId())
                .nickName(chat.getDiary().getMember().getNickname())
                .createdAt(chat.getDiary().getCreatedAt())
                .content(chat.getDiary().getContent())
                .build();
    }

    private ChatLyricsResponseDto createChatLyricsResponseDto(Chat chat) {
        ChatResponseDto chatResponseDto = chatService.createChatResponseDto(chat);
        return ChatLyricsResponseDto.builder()
                .chatResponseDto(chatResponseDto)
                .generatedLyrics(chat.getLyrics())
                .build();
    }

    /**
     * 북마크된 작사 목록 조회
     * 
     * @param email 조회를 요청하는 회원의 이메일
     * @param pageable 페이지 정보
     * @return 북마크된 작사 목록이 포함된 응답
     */
    public BaseResponse<BookmarkAllLyricsResponseDto> getAllBookmarkLyrics(String email, Pageable pageable) {
        Member member = getMemberByEmail(email);
        
        Page<Bookmark> bookmarkPage = bookmarkRepository.findAllByMemberAndIsBookmarkTrueAndChat_LyricsIsNotNull(member, pageable);
        System.out.println("작사 추천: "+bookmarkPage);
        
        List<BookmarkAllLyricsResponseDto.BookmarkLyricsDto> bookmarkDtos = bookmarkPage.getContent().stream()
                .map(bookmark -> BookmarkAllLyricsResponseDto.BookmarkLyricsDto.builder()
                        .diaryId(bookmark.getChat().getDiary().getId())
                        .chatId(bookmark.getChat().getId())
                        .diaryContent(bookmark.getChat().getDiary().getContent())
                        .generatedLyrics(bookmark.getChat().getLyrics())
                        .build())
                .collect(Collectors.toList());

        PageDto pageInfo = PageDto.builder()
                .pageSize(pageable.getPageSize())
                .currentPage(pageable.getPageNumber() + 1)
                .totalCount(bookmarkPage.getTotalElements())
                .isLastPage(bookmarkPage.isLast())
                .build();

        BookmarkAllLyricsResponseDto responseDto = BookmarkAllLyricsResponseDto.builder()
                .pageInfo(pageInfo)
                .bookmarks(bookmarkDtos)
                .build();

        return new BaseResponse<>(responseDto);
    }

    /**
     * 북마크된 노래 추천 목록 조회
     *
     * @param email 조회를 요청하는 회원의 이메일
     * @param pageable 페이지 정보
     * @return 북마크된 노래 추천 목록이 포함된 응답
     */
    public BaseResponse<BookmarkAllRecommendResponseDto> getAllBookmarkRecommend(String email, Pageable pageable) {
        Member member = getMemberByEmail(email);
        Page<Bookmark> bookmarkPage = bookmarkRepository.findAllByMemberAndIsBookmarkTrueAndChat_RecommendIsNotNull(member, pageable);
        System.out.println("노래 추천: "+bookmarkPage);

        List<BookmarkAllRecommendResponseDto.BookmarkRecommendDto> bookmarkDtos = bookmarkPage.getContent().stream()
                .map(bookmark ->
                        {
                            List<Long> songIds = songRepository.findAllByChatId(bookmark.getChat().getId())
                                    .stream()
                                    .map(Song::getId)
                                    .toList();

                            return BookmarkAllRecommendResponseDto.BookmarkRecommendDto.builder()
                                    .diaryId(bookmark.getChat().getDiary().getId())
                                    .songIds(songIds)
                                    .diaryContent(bookmark.getChat().getDiary().getContent())
                                    .build();
                        }
                )
                .toList();
        PageDto pageInfo = PageDto.builder()
                .pageSize(pageable.getPageSize())
                .currentPage(pageable.getPageNumber()+1)
                .totalCount(bookmarkPage.getTotalElements())
                .isLastPage(bookmarkPage.isLast())
                .build();

        return new BaseResponse<>(BookmarkAllRecommendResponseDto.builder()
                .pageInfo(pageInfo)
                .bookmarks(bookmarkDtos)
                .build());
    }
}
