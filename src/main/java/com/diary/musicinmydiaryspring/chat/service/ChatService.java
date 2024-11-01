package com.diary.musicinmydiaryspring.chat.service;

import com.diary.musicinmydiaryspring.bookmark.service.BookmarkService;
import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.entity.Chat;
import com.diary.musicinmydiaryspring.chat.repository.ChatRepository;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RestClient restClient;
    private final ChatRepository chatRepository;
    private final BookmarkService bookmarkService;

    /**
     * FastAPI에게 응답을 요청하는 메서드
     * @param chatRequestDto 요청 데이터
     * return ChatResponseDto 응답 객체
     * */
    public ChatResponseDto requestChatResponse(ChatRequestDto chatRequestDto){
        String url = "http://diary-music.kro.kr/api/question/";

        ResponseEntity<ChatResponseDto> chatResponseDto = restClient.post()
                .uri(url)
                .body(chatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CustomRuntimeException(BaseResponseStatus.INTERNAL_CLIENT_ERROR);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CustomRuntimeException(BaseResponseStatus.SERVER_ERROR);
                })
                .toEntity(ChatResponseDto.class);

        return chatResponseDto.getBody();
    }

    /**
     * FastAPI의 응답값을 저장하고 사용자에게 반환하는 메서드
     * @param chatRequestDto 요청 객체`
     * return BaseResponse<ChatResponseDto> 응답 객체
     * */
    @Transactional
    public BaseResponse<ChatResponseDto> saveChatAndResponse(ChatRequestDto chatRequestDto){
        ChatResponseDto chatResponseDto = requestChatResponse(chatRequestDto);

        Chat chat = Chat.builder()
                .createdAt(chatResponseDto.getCreatedAt() != null ? chatResponseDto.getCreatedAt() : LocalDateTime.now())
                .chatResponse(chatResponseDto.getChatResponse())
                .isLiked(false)
                .build();

        chatRepository.save(chat);

        return new BaseResponse<>(ChatResponseDto.builder()
                .createdAt(LocalDateTime.now())
                .chatResponse(chatResponseDto.getChatResponse())
                .build());
    }


    /**
     * Chat 엔티티의 isLiked 필드를 true로 업데이트하고 북마크 등록하는 메서드
     * @param chatId Chat 엔티티의 ID
     * @return BaseResponse<ChatResponseDto> 응답 객체
     */
    @Transactional
    public BaseResponse<ChatResponseDto> likeChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_CHAT));

        chat.setIsLiked(true);
        chatRepository.save(chat);
        bookmarkService.addBookmark(chat);

        return new BaseResponse<>(ChatResponseDto.builder()
                .id(chat.getId())
                .createdAt(chat.getCreatedAt() != null ? chat.getCreatedAt() : LocalDateTime.now())
                .chatResponse(chat.getChatResponse() != null ? chat.getChatResponse() : "")
                .isLiked(true)
                .build());
    }
}
