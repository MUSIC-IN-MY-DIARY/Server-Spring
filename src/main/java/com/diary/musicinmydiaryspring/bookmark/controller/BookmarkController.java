package com.diary.musicinmydiaryspring.bookmark.controller;

import com.diary.musicinmydiaryspring.bookmark.dto.*;
import com.diary.musicinmydiaryspring.bookmark.service.BookmarkService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PutMapping("/{chatId}")
    public BaseResponse<BookmarkResponseDto> updateBookmark(
            @PathVariable Long chatId,
            Principal principal
    ){
        String email = principal.getName();
        return bookmarkService.updateBookmark(email, chatId);
    }


    @GetMapping("/detail/generate-lyrics")
    public BaseResponse<BookmarkDetailLyricsResponseDto> getDetailBookmarkLyrics(
            @RequestParam Long chatId,
            Principal principal
    ){
        String email = principal.getName();
        return bookmarkService.getDetailBookmarkLyrics(email, chatId);

    }

    @GetMapping("/detail/recommend-songs")
    public BaseResponse<BookmarkDetailRecommendResponseDto> getDetailBookmarkRecommend(
            @RequestParam Long songId,
            Principal principal
    ){
        String email = principal.getName();
        return bookmarkService.getDetailBookmarkRecommend(email, songId);
    }

    @GetMapping("/all/generate-lyrics")
    public BaseResponse<BookmarkAllLyricsResponseDto> getAllBookmarkLyrics(
            @PageableDefault(size = 3) Pageable pageable,
            Principal principal
    ) {
        String email = principal.getName();
        return bookmarkService.getAllBookmarkLyrics(email, pageable);
    }

    @GetMapping("/all/recommend-songs")
    public BaseResponse<BookmarkAllRecommendResponseDto> getAllBookmarkRecommend(
            @PageableDefault(size=3) Pageable pageable,
            Principal principal
    ){
        String email = principal.getName();
        return bookmarkService.getAllBookmarkRecommend(email, pageable);
    }

}
