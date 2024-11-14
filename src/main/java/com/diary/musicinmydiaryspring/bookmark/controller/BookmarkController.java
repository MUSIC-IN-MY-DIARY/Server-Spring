package com.diary.musicinmydiaryspring.bookmark.controller;

import com.diary.musicinmydiaryspring.bookmark.dto.BookmarkResposneDto;
import com.diary.musicinmydiaryspring.bookmark.service.BookmarkService;
import com.diary.musicinmydiaryspring.chat.dto.generate.ChatLyricsResponseDto;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PutMapping("/{chatId}")
    public BaseResponse<BookmarkResposneDto> updateBookmark(
            @PathVariable Long chatId,
            Principal principal
    ){
        String email = principal.getName();
        return bookmarkService.updateBookmark(email, chatId);
    }


    @GetMapping("/detail/generate-lyrics")
    public BaseResponse<ChatLyricsResponseDto> getDetailBookmarkLyrics(
            @RequestParam Long chatId,
            Principal principal
    ){
        String email = principal.getName();
        // 1. 가사 만든 경우엔 Chat 테이블을 뒤져야 함
        return bookmarkService.getDetailBookmarkLyrics(email, chatId);

    }
    // 2. 노래 추천 받은 경우엔 Song 테이블을 뒤져야 함






}
