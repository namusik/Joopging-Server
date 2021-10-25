package com.project.joopging.dto.user;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
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

    private Location location;

    private Type type;

    private Distance distance;
}
