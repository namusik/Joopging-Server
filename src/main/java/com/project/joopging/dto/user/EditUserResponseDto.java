package com.project.joopging.dto.user;

import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.model.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "유저 업데이트 응답", description = "유저 업데이트 응답 DTO")
public class EditUserResponseDto {
    private Long id;

    private String userImg;

    private String location;

    private String type;

    private String distance;

    private UserRoleEnum role;

    private String intro;

    public EditUserResponseDto(User user) {
        this.id = user.getId();
        this.userImg = user.getUserImg();
        this.location = user.getLocation().getName();
        this.type = user.getType().getName();
        this.distance = user.getDistance().getName();
        this.role = user.getRole();
        this.intro = user.getIntro();
    }
}
