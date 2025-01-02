package com.diary.musicinmydiaryspring.chat.service;

import com.diary.musicinmydiaryspring.bookmark.repository.BookmarkRepository;
import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.chat.repository.ChatRepository;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.common.utils.JsonParser;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.repository.DiaryRepository;
import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import com.diary.musicinmydiaryspring.song.entity.Song;
import com.diary.musicinmydiaryspring.song.repository.SongRepository;
import com.diary.musicinmydiaryspring.song.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

import static com.diary.musicinmydiaryspring.chat.entity.Chat.createChatWithLyrics;
import static com.diary.musicinmydiaryspring.chat.entity.Chat.createChat;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final RestClient restClient;
    private final ChatRepository chatRepository;
    private final DiaryRepository diaryRepository;
    private final SongRepository songRepository;


    private static final String RECOMMEND_URL = "http://127.0.0.1:8000/api/recommend";
    private static final String LYRICS_URL = "http://127.0.0.1:8000/api/generate";
    private final SongService songService;
    private final BookmarkRepository bookmarkRepository;

    /**
     * 모델 서버에 노래 추천 요청을 보내고 결과를 저장
     *
     * @param recommendRequestDto 노래 추천 요청에 필요한 데이터
     * @param diaryId 다이어리 ID
     * @return ChatRecommendResponseDto 추천된 노래 목록과 관련 정보를 담은 응답 객체
     */
    @Transactional
    public ChatRecommendResponseDto requestAndSaveChatRecommendSongs(ChatRequestDto recommendRequestDto, Long diaryId){
        ChatRecommendResponseDto recommendResponse = postRequest(RECOMMEND_URL, recommendRequestDto, ChatRecommendResponseDto.class);

        String beforeParsingJsonData = recommendResponse.getRecommendedSongs();
        List<SongResponseDto> songResponseDtos = JsonParser.parseSongList(beforeParsingJsonData);


        Diary diary = findDiaryById(diaryId);
        Chat chat = saveChatWithSongs(diary);

        for (SongResponseDto songResponseDto : songResponseDtos) {
            Song song = Song.createSongWithChat(
                    chat,
                    songResponseDto.getArtist(),
                    songResponseDto.getSongTitle(),
                    songResponseDto.getGenre()
                    );

            songRepository.save(song);
        }

        List<Long> songIds = songService.findSongIdsByChatId(chat.getId());

        ChatResponseDto chatResponseDto = createChatResponseDto(chat, false);

        return ChatRecommendResponseDto.builder()
                .chatResponseDto(chatResponseDto)
                .recommendedSongs(recommendResponse.getRecommendedSongs())
                .songId(songIds)
                .build();
    }

    /**
     * FastAPI에 가사 생성 요청을 보내고 결과를 저장
     *
     * @param lyricsRequestDto 가사 생성 요청에 필요한 데이터
     * @param diaryId 요청과 연관된 다이어리 ID
     * @return ChatLyricsResponseDto 생성된 가사와 관련 정보를 담은 응답 객체
     */
    @Transactional
    public ChatLyricsResponseDto requestChatGenerateLyrics(ChatRequestDto lyricsRequestDto, Long diaryId) {

        ChatLyricsResponseDto lyricsResponse = postRequest(LYRICS_URL, lyricsRequestDto, ChatLyricsResponseDto.class);

        Diary diary = findDiaryById(diaryId);
        Chat chat = saveChatWithLyrics(diary, lyricsResponse.getGeneratedLyrics());

        ChatResponseDto chatResponseDto = createChatResponseDto(chat, false);

        return ChatLyricsResponseDto.builder()
                .chatResponseDto(chatResponseDto)
                .generatedLyrics(lyricsResponse.getGeneratedLyrics())
                .build();
    }

    /**
     * 주어진 URL로 POST 요청을 보내고, 응답을 지정된 타입으로 반환
     * 요청 중 발생할 수 있는 오류를 HTTP 상태 코드에 따라 처리
     *
     * @param url 요청을 보낼 URL
     * @param requestDto 요청에 필요한 데이터
     * @param responseType 응답 타입 클래스
     * @param <T> 응답 객체 타입
     * @return 요청 응답 객체
     */
    private <T> T postRequest(String url, Object requestDto, Class<T> responseType){
        return restClient.post()
                .uri(url)
                .body(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CustomRuntimeException(BaseResponseStatus.INTERNAL_CLIENT_ERROR);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CustomRuntimeException(BaseResponseStatus.SERVER_ERROR);
                })
                .toEntity(responseType)
                .getBody();
    }

    /**
     * Chat 엔티티를 기반으로 ChatResponseDto를 생성
     *
     * @param chat Chat 엔티티
     * @return ChatResponseDto 생성된 Chat 응답 객체
     */
    public ChatResponseDto createChatResponseDto(Chat chat, boolean isBookmarked){
        return ChatResponseDto.builder()
                .id(chat.getId())
                .diaryId(chat.getDiary().getId())
                .createdAt(chat.getCreatedAt() != null ? chat.getCreatedAt() : LocalDateTime.now())
                .isBookmarked(isBookmarked)
                .build();
    }

    /**
     * 다이어리 ID로 Diary 엔티티를 조회
     *
     * @param diaryId 조회할 다이어리 ID
     * @return 조회된 Diary 엔티티
     * @throws CustomRuntimeException 다이어리가 존재하지 않을 때 발생
     */
    public Diary findDiaryById(Long diaryId){
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_DIARY));
    }

    /**
     * 다이어리에 연관된 Chat 엔티티를 생성하고 저장
     *
     * @param diary Chat과 연관된 Diary 엔티티
     * @return 저장된 Chat 엔티티
     */
    private Chat saveChatWithSongs(Diary diary) {
        Chat chat = createChat(diary);
        return chatRepository.save(chat);
    }

    /**
     * 작사를 추천받고 Chat에 저장
     *
     * @param diary Chat과 연관된 Diary 엔티티
     * @param lyrics Chat에 저장한 lyrics
     * @return Chat 저장된 Chat 엔티티
     * */
    private Chat saveChatWithLyrics(Diary diary, String lyrics) {
        Chat chat = createChatWithLyrics(diary, lyrics);
        return chatRepository.save(chat);
    }



}
