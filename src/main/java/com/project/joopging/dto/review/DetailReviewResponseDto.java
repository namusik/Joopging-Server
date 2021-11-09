package com.project.joopging.dto.review;

import com.project.joopging.model.Post;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DetailReviewResponseDto {
    private Long reviewId;
    private String title;
    private String content;
    private String reviewImg;
    private int star;
    private int satiRate;
    private int levelRate;
    private int trashRate;
    private Long userId;
    private String nickname;
    private String userImg;

    public DetailReviewResponseDto(Review review, User user) {
        this.reviewId = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.reviewImg = review.getReviewImg();
        this.star = review.getStar();
        this.satiRate = review.getSatiRate();
        this.levelRate = review.getLevelRate();
        this.trashRate = review.getTrashRate();
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.userImg = user.getUserImg();
    }
}
