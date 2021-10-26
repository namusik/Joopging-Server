package com.project.joopging.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    private String email;

    private String nickname;

    private String password;

    private Integer location;

    private Integer type;

    private Integer distance;
}
