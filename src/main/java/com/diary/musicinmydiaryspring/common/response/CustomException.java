package com.diary.musicinmydiaryspring.common.response;

public class CustomException extends RuntimeException{

    private final BaseResponseStatus status;

    public CustomException(BaseResponseStatus status){
        super(status.getMessage());
        this.status = status;
    }

    public BaseResponseStatus getStatus(){
        return status;
    }
}