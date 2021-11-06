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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "후기 아이디")
    private Long id;

    @Column
    @ApiModelProperty(value = "후기 제목")
    private String title;
    
    @Column(nullable = false, length = 1000)
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
    @ApiModelProperty(value = "게시글 정보")
    private Post postReview;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    private User userReview;

    public Review(ReviewRequestDto requestDto, Post post, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.reviewImg = requestDto.getReviewImg();
        this.star = requestDto.getStar();
        this.postReview = post;
        this.userReview = user;
    }

    public void update(ReviewRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.reviewImg = requestDto.getReviewImg();
        this.star = requestDto.getStar();
    }
}
