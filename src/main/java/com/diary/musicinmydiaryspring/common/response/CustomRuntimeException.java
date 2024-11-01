package com.diary.musicinmydiaryspring.common.response;

public class CustomRuntimeException extends RuntimeException{

    private final BaseResponseStatus status;

    public CustomRuntimeException(BaseResponseStatus status){
        super(status.getMessage());
        this.status = status;
    }

    public BaseResponseStatus getStatus(){
        return status;
    }
}

