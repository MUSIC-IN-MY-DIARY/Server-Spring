package com.diary.musicinmydiaryspring.diary.service;

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
import com.diary.musicinmydiaryspring.song.entity.Song;
import com.diary.musicinmydiaryspring.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final SongRepository songRepository;
    private final WebClient webClient;

    /**
     * 멤버 아이디로 일기 작성
     * @param memberId 멤버 아이디
     * @param diaryRequestDto 일기 Dto
     * */
    @Transactional
    public BaseResponse<DiaryResponseDto> wirteDiary(Long memberId, DiaryRequestDto diaryRequestDto) {
        Member member = memberRepository.findById(memberId)
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
     *
     * */
    @Transactional
    public BaseResponse<SongResponseDto> sendDiaryAndGetChat(SongRequestDto songRequestDto){
        SongResponseDto songResponseDto =  webClient.post()
                        .uri("/dashboard/chat")
                        .bodyValue(songRequestDto)
                        .retrieve()
                        .bodyToMono(SongResponseDto.class)
                        .block();

        return new BaseResponse<>(songResponseDto);
    }

}