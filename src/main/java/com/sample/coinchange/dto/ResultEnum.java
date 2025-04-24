package com.sample.coinchange.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {
    SUCCESS(200, "Success"),
    BAD_INPUT(400, "Bad Input"),
    FAILED(400, "Failed"),
    ERROR(500, "Error");

    private final Integer code;
    private final String msg;
}
