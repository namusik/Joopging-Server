package com.project.joopging.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {
    private LoginDetailReponseDto user;
    private String jwtToken;
}
