package com.diary.musicinmydiaryspring.diary.service;


import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.chat.service.ChatService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.repository.DiaryRepository;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final ChatService chatService;

    /**
     * 일기를 저장하고 노래 추천을 요청한다.
     * @param diaryRequestDto 일기 요청 데이터
     * @param email           사용자 이메일
     * @return 추천 노래 리스트를 담은 응답 객체
     */
    @Transactional
    public BaseResponse<ChatRecommendResponseDto> recommendSongs(DiaryRequestDto diaryRequestDto, String email) {
        Diary diary = saveDiary(email, diaryRequestDto);
        ChatRecommendResponseDto recommendResponseDto = processChatRecommendResponse(diaryRequestDto, diary.getId());
        return new BaseResponse<>(recommendResponseDto);
    }

    /**
     * 일기를 저장하고 작사 생성 요청을 처리한다.
     *
     * @param diaryRequestDto 일기 요청 데이터
     * @param email           사용자 이메일
     * @return 생성된 가사를 담은 응답 객체
     */
    @Transactional
    public BaseResponse<ChatLyricsResponseDto> generateLyrics(DiaryRequestDto diaryRequestDto, String email) {
        Diary diary = saveDiary(email, diaryRequestDto);
        ChatLyricsResponseDto lyricsResponseDto = processChatLyricsResponse(diaryRequestDto, diary.getId());
        return new BaseResponse<>(lyricsResponseDto);
    }

    /**
     * 챗봇을 통해 노래 추천을 요청한다.
     *
     * @param diaryRequestDto 일기 요청 데이터
     * @param diaryId         저장된 일기의 ID
     * @return 추천 노래 리스트를 담은 DTO
     */
    public ChatRecommendResponseDto processChatRecommendResponse(DiaryRequestDto diaryRequestDto, Long diaryId) {
        ChatRequestDto chatRequestDto = createChatRequest(diaryRequestDto);
        ChatRecommendResponseDto recommendResponseDto = chatService.requestAndSaveChatRecommendSongs(chatRequestDto, diaryId);
        return recommendResponseDto;
    }

    /**
     * 챗봇을 통해 작사 생성을 요청한다.
     *
     * @param diaryRequestDto 일기 요청 데이터
     * @param diaryId         저장된 일기의 ID
     * @return 생성된 가사를 담은 DTO
     */
    public ChatLyricsResponseDto processChatLyricsResponse(DiaryRequestDto diaryRequestDto, Long diaryId) {

        ChatRequestDto chatRequestDto = createChatRequest(diaryRequestDto);
        ChatLyricsResponseDto lyricsResponseDto = chatService.requestChatGenerateLyrics(chatRequestDto, diaryId);
        return lyricsResponseDto;
    }



    /**
     * 일기 저장 로직
     * @param diaryRequestDto 일기 Dto
     * @param email 사용자 이메일
     * @return 저장된 Diary 객체
     */
    public Diary saveDiary(String email, DiaryRequestDto diaryRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_MEMBER));

        Diary diary = Diary.builder()
                .member(member)
                .createdAt(LocalDateTime.now())
                .content(diaryRequestDto.getDiaryContent())
                .build();

        return diaryRepository.save(diary);
    }

    /**
     * ChatRequestDto 생성 로직
     * @param diaryRequestDto 일기 Dto
     * @return 생성된 ChatRequestDto 객체
     */
    private ChatRequestDto createChatRequest(DiaryRequestDto diaryRequestDto){
        return ChatRequestDto.builder()
                .diaryContent(diaryRequestDto.getDiaryContent())
                .build();
    }
}

