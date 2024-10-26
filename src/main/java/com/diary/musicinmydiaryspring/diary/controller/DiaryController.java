package com.diary.musicinmydiaryspring.diary.controller;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.utils.RequestParser;
import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.entity.Diary;
import com.diary.musicinmydiaryspring.diary.service.DiaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryservice;

    @PostMapping("/write")
    public BaseResponse<DiaryResponseDto> writeDiary(
            @Validated @RequestBody DiaryRequestDto diaryRequestDto,
            @AuthenticationPrincipal Principal principal
            ){

        if (diaryRequestDto == null){
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST_INPUT);
        }
        String email = principal.getName();

        return diaryservice.writeDiary(diaryRequestDto, email);
    }
}