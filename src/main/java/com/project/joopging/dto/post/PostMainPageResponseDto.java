package com.project.joopging.dto.post;

import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Getter
@NoArgsConstructor
public class PostMainPageResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String runningDate;
    private Long dDay;
    private String location;
    private String type;
    private String distance;
    private int limitPeople;
    private int nowPeople;
    private String postImg;
    private Integer viewCount;
    private Long userId;
    private String nickname;
    private String userImg;
    private boolean bookMarkInfo;

    public PostMainPageResponseDto(Post post, User writer, Boolean checkBookMark) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        LocalDateTime runningDate = post.getRunningDate();
        String day = runningDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(runningDate);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        this.runningDate = ts[0] + " ("+day+") " + ts[1];
        this.dDay = ChronoUnit.DAYS.between(LocalDate.now(), post.getEndDate());
        this.location = post.getLocation();
        this.type = post.getType();
        this.distance = post.getDistance();
        this.limitPeople = post.getLimitPeople();
        this.nowPeople = post.getNowPeople();
        this.postImg = post.getPostImg();
        this.viewCount = post.getViewCount();
        this.bookMarkInfo = checkBookMark;
        this.userId = writer.getId();
        this.nickname = writer.getNickname();
        this.userImg = writer.getUserImg();
    }
}
