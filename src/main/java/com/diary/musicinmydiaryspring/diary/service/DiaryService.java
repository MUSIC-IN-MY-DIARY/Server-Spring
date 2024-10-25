package com.diary.musicinmydiaryspring.diary.service;

import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.service.ChatService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomException;
import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.repository.DiaryRepository;
import com.diary.musicinmydiaryspring.member.entity.Member;
import com.diary.musicinmydiaryspring.member.repsitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final ChatService chatService;

    /**
     * 일기 작성 후 챗봇 응답 리턴
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

        ChatRequestDto chatRequestDto = ChatRequestDto.builder()
                .memberId(member.getId())
                .diaryContent(diary.getContent())
                .build();

        BaseResponse<ChatResponseDto> chatResponse = chatService.saveChatAndResponse(chatRequestDto);
        ChatResponseDto chatResult = chatResponse.getResult();

        return new BaseResponse<>(DiaryResponseDto.builder()
                .nickName(member.getNickname())
                .createdAt(diary.getCreatedAt())
                .updatedAt(diary.getUpdatedAt())
                .content(diary.getContent())
                .chatResponse(chatResult != null? chatResult.getChatResponse() : "")
                .build());
    }

}