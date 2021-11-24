package com.project.joopging.dto.post;


import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
public class PostSearchesDto {
    //Post 정보
    private  Long postId;
    private  String title;
    private  String content;
    private  String runningDate;
    private  LocalDate startDate;
    private  LocalDate endDate;
    private  String location;
    private  String type;
    private  String distance;
    private  int limitPeople;
    private  int nowPeople;
    private  String postImg;
    private  Integer viewCount;
    private boolean bookMarkInfo;
    private Long dDay;

    //user 정보
    private Long userId;
    private String nickname;
    private String userImg;
    public PostSearchesDto(Post post, User writer, Boolean bookMarkInfo,String runningDateToString) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.runningDate = runningDateToString;
        this.startDate = post.getStartDate();
        this.endDate = post.getEndDate();
        this.location = post.getLocation();
        this.type = post.getType();
        this.distance = post.getDistance();
        this.limitPeople = post.getLimitPeople();
        this.nowPeople = post.getNowPeople();
        this.postImg = post.getPostImg();
        this.viewCount = post.getViewCount();
        this.bookMarkInfo = bookMarkInfo;
        this.dDay = ChronoUnit.DAYS.between(LocalDate.now(), post.getEndDate());
        this.userId = writer.getId();
        this.nickname = writer.getNickname();
        this.userImg = writer.getUserImg();
    }

    public PostSearchesDto(Post post, User writer,String runningDateToString) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.runningDate =runningDateToString;
        this.startDate = post.getStartDate();
        this.endDate = post.getEndDate();
        this.location = post.getLocation();
        this.type = post.getType();
        this.distance = post.getDistance();
        this.limitPeople = post.getLimitPeople();
        this.nowPeople = post.getNowPeople();
        this.postImg = post.getPostImg();
        this.viewCount = post.getViewCount();
        this.bookMarkInfo = false;
        this.dDay = ChronoUnit.DAYS.between(LocalDate.now(), post.getEndDate());
        this.userId = writer.getId();
        this.nickname = writer.getNickname();
        this.userImg = writer.getUserImg();
    }

}
