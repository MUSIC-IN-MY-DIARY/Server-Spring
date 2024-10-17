package com.diary.musicinmydiaryspring.diary.controller;

import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DiaryController {
    private final DiaryService diaryservice;

    @SneakyThrows
    @PostMapping("/{memberId}/diary")
    public ResponseEntity<DiaryResponseDto> writeDiary(
            @PathVariable Long memberId,
            @Validated @RequestBody DiaryRequestDto diaryRequestDto
            ){

        if (memberId == null || memberId < 0){
            throw new BadRequestException("해당 멤버가 조회되지 않습니다.");
        }

        if (diaryRequestDto == null){
            throw new BadRequestException("일기가 작성되지 않았습니다.");
        }

        Diary diary = diaryservice.wirteDiary(memberId, diaryRequestDto);
        DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                .id(diary.getId())
                .nickName(diary.getMember().getNickname())
                .createdAt(diary.getCreatedAt())
                .content(diary.getContent())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(diaryResponseDto);
    }
}
