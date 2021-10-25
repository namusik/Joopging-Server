package com.project.joopging.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private String result;

    private String msg;

    private Object object;

    public ResponseDto(String result, String msg, Object object) {
        this.result = result;
        this.msg = msg;
        this.object = object;
    }
}