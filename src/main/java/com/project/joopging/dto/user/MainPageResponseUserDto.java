package com.project.joopging.dto.user;

import com.project.joopging.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainPageResponseUserDto {
    private String nickname;
    private String location;
    private String distance;
    private String type;

    public MainPageResponseUserDto(User user) {
        this.nickname = user.getNickname();
    }
}
