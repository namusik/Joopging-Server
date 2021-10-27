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

    private Integer location;

    private Integer type;

    private Integer distance;

    private String intro;
}
