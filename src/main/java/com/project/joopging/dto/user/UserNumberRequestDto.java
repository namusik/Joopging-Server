package com.project.joopging.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserNumberRequestDto {

    private final Long userId;
}
