package com.diary.musicinmydiaryspring.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {

    /**
     * 200 : 요청 성공
     * */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하셨습니다"),

    /**
     * 400 : 요청 실패
     */

    UNAUTHORIZED(false, HttpStatus.UNAUTHORIZED.value(), "인증이 필요합니다. 로그인을 시도해 주세요."),
    INVALID_ACCESS_TOKEN(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 액세스 토큰입니다."),
    EXPIRED_ACCESS_TOKEN(false, HttpStatus.UNAUTHORIZED.value(), "액세스 토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(false, HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(false, HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 존재하지 않습니다."),
    REFRESH_TOKEN_MISMATCH(false, HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 일치하지 않습니다."),
    TOKEN_SIGNATURE_INVALID(false, HttpStatus.UNAUTHORIZED.value(), "토큰 서명이 유효하지 않습니다."),
    BAD_REQUEST_INPUT(false, HttpStatus.BAD_REQUEST.value(), "입력 데이터가 유효하지 않습니다."),
    INVALID_JSON_FORMAT(false, HttpStatus.BAD_REQUEST.value(), "잘못된 JSON 형식입니다"),
    

    NOT_FOUND_DIARY(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 다이어리입니다."),
    NOT_FOUND_DIARY_ID(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 다이어리 아이디입니다."),

    NOT_FOUND_MEMBER(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 멤버입니다."),
    NOT_FOUND_MEMBER_ID(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 멤버 아이디입니다."),

    NOT_FOUND_SONG(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 노래입니다."),
    NOT_FOUND_SONG_ID(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 노래 아이디입니다."),

    NOT_FOUND_ALBUM(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 엘범입니다."),
    NOT_FOUND_ALBUM_ID(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 엘범 아이디입니다."),

    NOT_FOUND_CHAT(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 채팅입니다."),
    NOT_FOUND_CHAT_ID(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 채팅 아이디입니다."),

    NOT_FOUND_BOOKMARK(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 북마크입니다."),
    NOT_FOUND_BOOKMARK_ID(false, HttpStatus.NOT_FOUND.value(), "존재하지 않은 북마크 아이디입니다."),

    ALREADY_EXIST_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일입니다."),
    ALREADY_ADD_BOOKMARK(false, HttpStatus.BAD_REQUEST.value(), "이미 북마크로 등록되었습니다."),
    INTERNAL_CLIENT_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "클라이언트 오류가 발생했습니다."),


    /**
     * 429: 너무 많은 요청
     * */

    TOO_MANY_REQUESTS(false, HttpStatus.TOO_MANY_REQUESTS.value(), "너무 많은 요청이 발생했습니다. 잠시 후 다시 시도해주세요."),


    /**
     * 500: Database, Server 오류
     * */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다."),
    INTERNAL_SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에서 예기치 못한 오류가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
