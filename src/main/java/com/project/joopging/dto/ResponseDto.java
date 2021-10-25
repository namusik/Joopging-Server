package com.project.joopging.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private Long statusCode;

    private String msg;

    private Object data;

    public ResponseDto(Long statusCode, String msg, Object data) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
    }
}