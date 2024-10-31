package com.diary.musicinmydiaryspring.bookmark.controller;

import com.diary.musicinmydiaryspring.bookmark.dto.BookmarkDetailResponseDto;
import com.diary.musicinmydiaryspring.bookmark.service.BookmarkService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/detail")
    public BaseResponse<BookmarkDetailResponseDto> getDetailBookmark(
            @RequestParam Long chatId
    ){
        return bookmarkService.getDetailBookmark(chatId);
    }

}
