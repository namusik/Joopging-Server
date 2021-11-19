package com.project.joopging.TestController;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "Default 응답", description = "Default 응답 DTO")
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