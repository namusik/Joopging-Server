package com.project.joopging.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserMyPageResponseDto {
    private int myBookmarks;
    private int myBadges;
    private int myReivews;
    private int myCrews;
}
