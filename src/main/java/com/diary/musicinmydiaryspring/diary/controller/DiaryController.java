package com.diary.musicinmydiaryspring.diary.controller;

import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.chat.dto.recommend.ChatRecommendResponseDto;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/recommend-songs")
    public BaseResponse<ChatRecommendResponseDto> recommendSongs(
            @Validated @RequestBody DiaryRequestDto diaryRequestDto,
            Principal principal
            ){

        if (diaryRequestDto == null){
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST_INPUT);
        }

        String email = principal.getName();
        return diaryService.recommendSongs(diaryRequestDto, email);
    }

    @PostMapping("/generate-lyrics")
    public BaseResponse<ChatLyricsResponseDto> generateLyrics(
            @Validated @RequestBody DiaryRequestDto diaryRequestDto,
            Principal principal
    ){
        if (diaryRequestDto == null){
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST_INPUT);
        }

        String email = principal.getName();
        return diaryService.generateLyrics(diaryRequestDto, email);
    }
}