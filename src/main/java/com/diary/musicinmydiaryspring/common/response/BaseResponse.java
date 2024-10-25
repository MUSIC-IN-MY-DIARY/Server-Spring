package com.diary.musicinmydiaryspring.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.diary.musicinmydiaryspring.common.response.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess","code","message","result"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final boolean isSuccess;

    @JsonProperty("code")
    private final int code;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공 응답
    public BaseResponse(T result){
        this.isSuccess = SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }

    // 실패 응답 (데이터 없이 상태만 반환)
    public BaseResponse(BaseResponseStatus status){
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getMessage();
        this.result = null;
    }

}
