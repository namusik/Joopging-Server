package com.project.joopging.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "로그인 유저 정보 응답", description = "로그인 유저 정보 응답 DTO")
public class LoginResponseDto {
    private LoginDetailReponseDto user;
    private String jwtToken;
}
