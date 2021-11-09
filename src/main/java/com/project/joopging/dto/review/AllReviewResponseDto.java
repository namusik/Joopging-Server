package com.project.joopging.dto.review;

import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
public class AllReviewResponseDto {
    private Long reviewId;
    private String title;
    private String content;
    private String reviewImg;
    private Long writeDateBefore;
    private int star;
    private int satiRate;
    private int levelRate;
    private int trashRate;
    private String nickname;
    private String userImg;

    public AllReviewResponseDto(Review review, User user) {
        this.reviewId = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.reviewImg = review.getReviewImg();
        this.writeDateBefore = ChronoUnit.DAYS.between(LocalDate.now(), review.getCreatedAt().toLocalDate());
        this.star = review.getStar();
        this.satiRate = review.getSatiRate();
        this.levelRate = review.getLevelRate();
        this.trashRate = review.getTrashRate();
        this.nickname = user.getNickname();
        this.userImg = user.getUserImg();
    }
}
