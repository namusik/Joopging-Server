package com.project.joopging.model;

import com.project.joopging.dto.review.ReviewRequestDto;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
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
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String reviewImg;

    @Column(nullable = false)
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
