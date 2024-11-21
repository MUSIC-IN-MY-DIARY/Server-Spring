package com.diary.musicinmydiaryspring.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;

import java.util.List;

public class JsonParser {
    public static List<SongResponseDto> parseSongList(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<SongResponseDto> songList = null;

        try {
            songList = objectMapper.readValue(jsonString, new TypeReference<List<SongResponseDto>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 핸들링 로직 추가
        }

        return songList;
    }
}
