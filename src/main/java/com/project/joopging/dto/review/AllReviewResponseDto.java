package com.project.joopging.dto.review;

import com.project.joopging.model.Post;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "전체 리뷰페이지 응답", description = "전체 리뷰페이지 응답 DTO")
public class AllReviewResponseDto {
    private Long reviewId;
    private String content;
    private String reviewImg;
    private int star;
    private String nickname;
    private String userImg;

    public AllReviewResponseDto(Review review, User user) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.reviewImg = review.getReviewImg();
        this.star = review.getStar();
        this.nickname = user.getNickname();
        this.userImg = user.getUserImg();
    }
}
