package com.project.joopging.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {

    private String email;

    private String nickname;

    private String password;

    private String location;

    private String type;

    private String distance;

    private String number;
}
