package com.project.joopging.dto.post;


import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Builder
@ApiModel(value = "게시글 상세 정보 응답", description = "게시글 상세 정보 응답 DTO")
public class PostDetailResponseDto {

    private final Long postId;
    private final String title;
    private final String content;
    private final LocalDate runningDate;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer location;
    private final Integer type;
    private final Integer distance;
    private final int limitPeople;
    private final int nowPeople;
    private final String postImg;
    private final Integer viewCount;

    //코멘트 넣기
}



