package com.project.joopging.dto.badge;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyBadgeListResponseDto {

    private final Long badgeId;
    private final String modifiedAt;
    private final int badgeCategory;
    private final int badgeLevel;


}
