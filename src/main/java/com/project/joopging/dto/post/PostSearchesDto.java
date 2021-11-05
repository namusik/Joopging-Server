package com.project.joopging.dto.post;


import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostSearchesDto {
    //Post 정보
    private  Long postId;
    private  String title;
    private  String content;
    private  LocalDateTime runningDate;
    private  LocalDate startDate;
    private  LocalDate endDate;
    private  String location;
    private  String type;
    private  String distance;
    private  int limitPeople;
    private  int nowPeople;
    private  String postImg;
    private  Integer viewCount;

    //user 정보
    private Long userId;
    private String nickname;
    private String userImg;

    public PostSearchesDto(Post post, User writer) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.runningDate = post.getRunningDate();
        this.startDate = post.getStartDate();
        this.endDate = post.getEndDate();
        this.location = post.getLocation().getName();
        this.type = post.getType().getName();
        this.distance = post.getDistance().getName();
        this.limitPeople = post.getLimitPeople();
        this.nowPeople = post.getNowPeople();
        this.postImg = post.getPostImg();
        this.viewCount = post.getViewCount();

        this.userId = writer.getId();
        this.nickname = writer.getNickname();
        this.userImg = writer.getUserImg();
    }
}
