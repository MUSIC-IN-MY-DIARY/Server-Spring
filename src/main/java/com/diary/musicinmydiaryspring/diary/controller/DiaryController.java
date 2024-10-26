package com.diary.musicinmydiaryspring.diary.controller;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.diary.dto.DiaryListResponseDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryRequestDto;
import com.diary.musicinmydiaryspring.diary.dto.DiaryResponseDto;
import com.diary.musicinmydiaryspring.diary.service.DiaryService;
import com.diary.musicinmydiaryspring.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/write")
    public BaseResponse<DiaryResponseDto> writeDiary(
            @Validated @RequestBody DiaryRequestDto diaryRequestDto,
            Principal principal
            ){

        if (diaryRequestDto == null){
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST_INPUT);
        }
        System.out.println(principal + "하...");
        String email = principal.getName();

        return diaryService.writeDiary(diaryRequestDto, email);
    }

    @GetMapping("/all")
    public BaseResponse<List<DiaryListResponseDto>> getAllDiary(
            @AuthenticationPrincipal Principal principal
    ){

        if (principal == null){
            return new BaseResponse<>(BaseResponseStatus.UNAUTHORIZED);
        }

        String email = principal.getName();
        return diaryService.getAllDiaries(email);
    }
}