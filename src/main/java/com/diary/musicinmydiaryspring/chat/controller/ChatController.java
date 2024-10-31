package com.diary.musicinmydiaryspring.chat.controller;

import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.service.ChatService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/get")
    public BaseResponse<ChatResponseDto> getChat(
            @Validated @RequestBody ChatRequestDto chatRequestDto
    )
    {
        return chatService.saveChatAndResponse(chatRequestDto);
    }

    @PutMapping("/like")
    public BaseResponse<ChatResponseDto> likeChat(
            @RequestParam Long chatId
    ){

        return chatService.likeChat(chatId);
    }
}
