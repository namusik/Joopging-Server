package com.project.joopging.model;

import com.project.joopging.dto.review.ReviewRequestDto;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ApiModel(value = "후기 정보")
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "후기 아이디")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "후기 내용")
    private String content;

    @Column(nullable = false)
    @ApiModelProperty(value = "후기 이미지")
    private String reviewImg;

    @Column(nullable = false)
    @ApiModelProperty(value = "후기 총 별점")
    private int star;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post postReview;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User userReview;

    public Review(ReviewRequestDto requestDto, Post post, User user) {
        this.content = requestDto.getContent();
        this.reviewImg = requestDto.getReviewImg();
        this.star = requestDto.getStar();
        this.postReview = post;
        this.userReview = user;
    }

    public void update(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.reviewImg = requestDto.getReviewImg();
        this.star = requestDto.getStar();
    }
}
