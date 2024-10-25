package com.diary.musicinmydiaryspring.chat.controller;

import com.diary.musicinmydiaryspring.chat.dto.ChatRequestDto;
import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.service.ChatService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (chatRequestDto == null){
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST_INPUT);
        }

        return chatService.saveChatAndResponse(chatRequestDto);
    }
}
