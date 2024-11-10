package com.diary.musicinmydiaryspring.chat.controller;

import com.diary.musicinmydiaryspring.chat.dto.ChatResponseDto;
import com.diary.musicinmydiaryspring.chat.service.ChatService;
import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;


    @PutMapping("/like")
    public BaseResponse<Boolean> likeChat(
            @RequestParam Long chatId
    ){

        if (chatId == null){
            return new BaseResponse<>(BaseResponseStatus.NOT_FOUND_CHAT_ID);
        }

        return chatService.updateChatIsLikedStatus(chatId);
    }
}
