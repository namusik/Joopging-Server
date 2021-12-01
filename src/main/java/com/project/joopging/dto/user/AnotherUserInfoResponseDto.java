package com.project.joopging.dto.user;

import com.project.joopging.model.Badge;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AnotherUserInfoResponseDto {

    //참여내역, 북마크, 후기, 배지 개수
    private final int bookmarkCount;
    private final int badgeCount;
    private final int reviewCount;
    private final int crewCount;

    //유저 정보
    private final Long id;
    private final String userImg;
    private final String nickname;
    private final String intro;
    private final String type;
    private final String distance;
    private final String location;
    private final List<Badge> badgeList;
}
