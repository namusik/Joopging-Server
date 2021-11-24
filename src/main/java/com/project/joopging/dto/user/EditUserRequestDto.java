package com.project.joopging.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequestDto {
    private String userImg;

    private String password;

    private String location;

    private String type;

    private String distance;

    private String intro;
}
