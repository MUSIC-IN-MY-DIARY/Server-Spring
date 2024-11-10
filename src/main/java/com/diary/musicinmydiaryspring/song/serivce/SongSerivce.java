package com.diary.musicinmydiaryspring.song.serivce;

import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.diary.musicinmydiaryspring.common.response.CustomRuntimeException;
import com.diary.musicinmydiaryspring.song.dto.SongResponseDto;
import com.diary.musicinmydiaryspring.song.entity.Song;
import com.diary.musicinmydiaryspring.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SongSerivce {
    private final SongRepository songRepository;

    @Transactional
    public Song getOrCreateSong(SongResponseDto songResponseDto){

        if (songResponseDto.getId() != null){
            return songRepository.findById(songResponseDto.getId())
                    .orElseThrow(() -> new CustomRuntimeException(BaseResponseStatus.NOT_FOUND_SONG));
        }

        return Song.builder()
                .albumTitle(songResponseDto.getAlbumTitle())
                .artist(songResponseDto.getArtist())
                .songTitle(songResponseDto.getSongTitle())
                .genre(songResponseDto.getGenre())
                .build();
    }
}
