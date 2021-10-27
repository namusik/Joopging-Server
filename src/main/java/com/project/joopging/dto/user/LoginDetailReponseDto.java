package com.project.joopging.dto.user;

import com.project.joopging.enums.UserRoleEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDetailReponseDto {
    private String nickname;

    private String password;

    private String email;

    private String location;

    private String type;

    private String distance;

    private String userImg;

    private UserRoleEnum role;
}
