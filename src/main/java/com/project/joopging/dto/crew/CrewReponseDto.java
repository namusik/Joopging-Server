package com.project.joopging.dto.crew;

import com.project.joopging.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CrewReponseDto {
    private Long userId;
    private String nickname;

    public CrewReponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
    }
}
