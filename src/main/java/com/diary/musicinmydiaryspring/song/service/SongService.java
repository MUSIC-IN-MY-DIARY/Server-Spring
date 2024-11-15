package com.diary.musicinmydiaryspring.song.service;

import com.diary.musicinmydiaryspring.song.entity.Song;
import com.diary.musicinmydiaryspring.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SongService {

    private final SongRepository songRepository;


    @Transactional
    public List<Long> findSongIdsByChatId(Long chatId) {
        return songRepository.findAllByChatId(chatId)
                .stream()
                .map(Song::getId)
                .toList();
    }

    public List<Long> findImageIdsByChatId(Long chatId) {
        return songRepository.findImageIdsByChatId(chatId)
                .stream()
                .map(Song::getImageId)
                .toList();
    }
}
