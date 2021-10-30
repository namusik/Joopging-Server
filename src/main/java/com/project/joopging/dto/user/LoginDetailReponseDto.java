package com.project.joopging.dto.user;

import com.project.joopging.enums.UserRoleEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "유저 상세 정보 응답", description = "유저 상세 정보 응답 DTO")
public class LoginDetailReponseDto {
    private Long id;

    private String nickname;

    private String email;

    private String location;

    private String type;

    private String distance;

    private String userImg;

    private UserRoleEnum role;

    private String intro;
}
