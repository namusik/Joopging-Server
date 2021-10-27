package com.project.joopging.dto.user;

import com.project.joopging.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {
    private LoginDetailReponseDto loginDetailReponseDto;
    private String jwtToken;
}
