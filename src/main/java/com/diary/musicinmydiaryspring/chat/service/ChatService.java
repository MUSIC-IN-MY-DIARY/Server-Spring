package com.diary.musicinmydiaryspring.chat.service;

import com.diary.musicinmydiaryspring.bookmark.service.BookmarkService;
import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.chat.repository.ChatRepository;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.repository.DiaryRepository;
import com.diary.musicinmydiaryspring.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

import static com.diary.musicinmydiaryspring.chat.entity.Chat.create;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final RestClient restClient;
    private final ChatRepository chatRepository;
    private final BookmarkService bookmarkService;
    private final DiaryRepository diaryRepository;

    private static final String RECOMMEND_URL = "https://diary-music.o-r.kr/api/recommend/";
    private static final String LYRICS_URL = "https://diary-music.o-r.kr/api/generate/";

    /**
     * 모델 서버에 노래 추천 요청을 보내고 결과를 저장
     *
     * @param recommendRequestDto 노래 추천 요청에 필요한 데이터
     * @param diaryId 다이어리 ID
     * @return ChatRecommendResponseDto 추천된 노래 목록과 관련 정보를 담은 응답 객체
     */
    public ChatRecommendResponseDto requestAndSaveChatRecommendSongs(ChatRequestDto recommendRequestDto, Long diaryId){
        ChatRecommendResponseDto recommendResponse = postRequest(RECOMMEND_URL, recommendRequestDto, ChatRecommendResponseDto.class);
        log.info("용학이 바보 : " + recommendResponse.getRecommendedSongs());
//        log.info("용학이 바보 : " + recommendResponse.getRecommendedSongs().toString());
        Diary diary = findDiaryById(diaryId);
        Chat chat = saveChat(diary, recommendResponse.toString());

        ChatResponseDto chatResponseDto = createChatResponseDto(chat);

        ChatRecommendResponseDto chatRecommendResponseDto = ChatRecommendResponseDto.builder()
                .chatResponseDto(chatResponseDto)
                .recommendedSongs(recommendResponse.getRecommendedSongs())
                .build();

        return chatRecommendResponseDto;
    }

    /**
     * 외부 서비스에 가사 생성 요청을 보내고 결과를 저장
     *
     * @param lyricsRequestDto 가사 생성 요청에 필요한 데이터
     * @param diaryId 요청과 연관된 다이어리 ID
     * @return ChatLyricsResponseDto 생성된 가사와 관련 정보를 담은 응답 객체
     */
    public ChatLyricsResponseDto requestChatGenerateLyrics(ChatRequestDto lyricsRequestDto, Long diaryId) {

        ChatLyricsResponseDto lyricsResponse = postRequest(LYRICS_URL, lyricsRequestDto, ChatLyricsResponseDto.class);

        Diary diary = findDiaryById(diaryId);
        Chat chat = saveChat(diary, lyricsResponse.toString());

        ChatResponseDto chatResponseDto = createChatResponseDto(chat);
        ChatLyricsResponseDto chatLyricsResponseDto = ChatLyricsResponseDto.builder()
                .chatResponseDto(chatResponseDto)
                .generatedLyrics(lyricsResponse.toString())
                .build();

        return chatLyricsResponseDto;
    }

    /**
     * 주어진 URL로 POST 요청을 보내고, 응답을 지정된 타입으로 반환합니다.
     * 요청 중 발생할 수 있는 오류를 HTTP 상태 코드에 따라 처리합니다.
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
    public ChatResponseDto createChatResponseDto(Chat chat){
        return ChatResponseDto.builder()
                .id(chat.getId())
                .diaryId(chat.getDiary().getId())
                .createdAt(chat.getCreatedAt() != null ? chat.getCreatedAt() : LocalDateTime.now())
                .isLiked(false)
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
     * @param chatResponse Chat에 저장할 챗봇 응답
     * @return 저장된 Chat 엔티티
     */
    private Chat saveChat(Diary diary, String chatResponse) {
        Chat chat = create(diary, chatResponse);
        return chatRepository.save(chat);
    }



    /**
     * Chat 엔티티의 isLiked 필드를 true로 업데이트하고 북마크 등록하는 메서드
     * @param chatId Chat 엔티티의 ID
     * @return BaseResponse<ChatResponseDto> 응답 객체
     */
    @Transactional
    public BaseResponse<Boolean> updateChatIsLikedStatus(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));

        chat.updateIsLiked();
        chatRepository.save(chat);
        bookmarkService.updateBookmark(chat);

        return new BaseResponse<>(chat.getIsLiked());

    }

}
