package com.diary.musicinmydiaryspring.diary.service;

import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomException;
import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.repository.DiaryRepository;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import com.diary.musicinmydiaryspring.song.dto.SongRequestDto;
import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final RestClient restClient;

    /**
     * 멤버 아이디로 일기 작성
     *
     * @param diaryRequestDto 일기 Dto
     * @param email
     */
    @Transactional
    public BaseResponse<DiaryResponseDto> wirteDiary(DiaryRequestDto diaryRequestDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(BaseResponseStatus.NOT_FOUND_MEMBER));

        Diary diary = Diary.builder()
                .member(member)
                .content(diaryRequestDto.getContent())
                .build();
        diaryRepository.save(diary);

        return new BaseResponse<>(DiaryResponseDto.builder()
                .nickName(member.getNickname())
                .content(diaryRequestDto.getContent())
                .build());
    }

    /**
     * FastAPI에게 응답을 요청하는 메서드
     * @param chatRequestDto
     * */
    public BaseResponse<ChatResponseDto> getChat(ChatRequestDto chatRequestDto){
        String url = "http://localhost:8000/chat";

        ResponseEntity<ChatResponseDto> chatResponseDto = restClient.post()
                .uri(url)
                .body(chatRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new CustomException(BaseResponseStatus.INTERNAL_CLIENT_ERROR);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new CustomException(BaseResponseStatus.SERVER_ERROR);
                })
                .toEntity(ChatResponseDto.class);

        return new BaseResponse<>(chatResponseDto.getBody());
    }

    /**
     * 사용자에게 챗봇 응답을 전달하는 메서드
     * return
     * */
}