package com.project.joopging.dto.user;

import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDetailsDto {
    private Long userid;
    private String userImg;
    private String password;
    private String location;
    private String type;
    private String distance;
    private String intro;
    private String nickname;
    private String number;
    private String email;

    public UserInfoDetailsDto(User user) {
        this.userid = user.getId();
        this.userImg = user.getUserImg();
        this.password = user.getPassword();
        this.location = user.getLocation();
        this.type = user.getType();
        this.distance = user.getDistance();
        this.intro = user.getIntro();
        this.nickname = user.getNickname();
        this.number = user.getNumber();
        this.email = user.getEmail();

    }
}
