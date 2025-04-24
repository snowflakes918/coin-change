package com.sample.coinchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseType<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ResponseType<T> success(T data) {
        return new ResponseType<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseType<T> error(ResultEnum resultEnum, String message) {
        return new ResponseType<>(resultEnum.getCode(), message, null);
    }
}
