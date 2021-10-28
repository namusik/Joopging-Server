package com.project.joopging.dto.review;

import com.project.joopging.model.Post;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DetailReviewResponseDto {
    private Long reviewId;
    private String content;
    private String reviewImg;
    private int star;
    private Long userId;
    private String nickname;
    private String userImg;
    private Long postId;
    private String postTitle;
    private String location;
    private LocalDate runningDate;
    private int limitPeople;

    public DetailReviewResponseDto(Review review, User user, Post post) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.reviewImg = review.getReviewImg();
        this.star = review.getStar();
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.userImg = user.getUserImg();
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.location = post.getLocation().getName();
        this.runningDate = post.getRunningDate().toLocalDate();
        this.limitPeople = post.getLimitPeople();
    }
}
