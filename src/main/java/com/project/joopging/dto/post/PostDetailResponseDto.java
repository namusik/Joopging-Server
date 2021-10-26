package com.project.joopging.dto.post;



import com.project.joopging.model.Comment;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


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

    //String 으로 돌려줄지 Integer 로 돌려줄지 물어보기
    private final String location;
    private final String type;
    private final String distance;



    private final int limitPeople;
    private final int nowPeople;
    private final String postImg;
    private final Integer viewCount;

    //코멘트 넣기

    private List<Comment> commentList;

}



