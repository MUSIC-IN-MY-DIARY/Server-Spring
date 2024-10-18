package com.diary.musicinmydiaryspring.diary.controller;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
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
    public BaseResponse<DiaryResponseDto> writeDiary(
            @PathVariable Long memberId,
            @Validated @RequestBody DiaryRequestDto diaryRequestDto
            ){

        if (memberId == null || memberId < 0){
            return new BaseResponse<>(BaseResponseStatus.NOT_FOUND_DIARY_ID);
        }

        if (diaryRequestDto == null){
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST_DIARY_INPUT);
        }

        Diary diary = diaryservice.wirteDiary(memberId, diaryRequestDto);
        DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                .id(diary.getId())
                .nickName(diary.getMember().getNickname())
                .createdAt(diary.getCreatedAt())
                .content(diary.getContent())
                .build();

        return new BaseResponse<>(diaryResponseDto);
    }
}
