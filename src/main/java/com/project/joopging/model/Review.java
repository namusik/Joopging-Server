package com.project.joopging.model;

import com.project.joopging.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    String reviewImg;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    Post postReview;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    User userReview;

    public Review(String content, String reviewImg, Post postReview, User userReview) {
        this.content = content;
        this.reviewImg = reviewImg;
        this.postReview = postReview;
        this.userReview = userReview;
    }
}
