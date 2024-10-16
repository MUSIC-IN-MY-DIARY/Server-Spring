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
import com.diary.musicinmydiaryspring.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final SongRepository songRepository;

    /**
     * 멤버 아이디로 일기 작성
     * @param memberId 멤버 아이디
     * @param diaryRequestDto 일기 Dto
     * */
    @Transactional
    public BaseResponse<DiaryResponseDto> wirteDiary(Long memberId, DiaryRequestDto diaryRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(BaseResponseStatus.NOT_FOUND_MEMBER));

//        Song song = songRepository.findById(diaryRequestDto.getSongId())
//                .orElseThrow(() -> new EntityNotFoundException("해당 노래가 조회되지 않습니다"));

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
}
