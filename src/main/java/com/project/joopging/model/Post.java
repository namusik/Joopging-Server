package com.project.joopging.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "게시글 정보")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "게시글 아이디")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 제목")
    private String title;

    @Column(nullable = false, length = 1000)
    @ApiModelProperty(value = "게시글 모임장 소개")
    private String crewHeadIntro;

    @Column(nullable = false, length = 1000)
    @ApiModelProperty(value = "게시글 내용")
    private String content;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 러닝 시작일")
    private LocalDateTime runningDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 모집 시작일")
    private LocalDate startDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 모집 마감일")
    private LocalDate endDate;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "게시글 지역")
    private String location;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "게시글 지형")
    private String type;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "게시글 거리")
    private String distance;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 최대 인원수")
    private int limitPeople;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 현재 인원수")
    private int nowPeople = 1;

    @Column
    @ApiModelProperty(value = "게시글 이미지")
    private String postImg;

    @Column
    @ApiModelProperty(value = "게시글 조회수")
    private Integer viewCount = 0;

    @Basic(fetch = FetchType.LAZY)
    @Formula("(select count(1) from book_mark bm where bm.post_id = id)")
    private Integer totalBookMarkCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    private User writer;

    @OneToMany(mappedBy = "postJoin", orphanRemoval = true)
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "참가자 정보")
    private List<Crew> Crew = new ArrayList<>();

    @OneToMany(mappedBy = "postReview")
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "후기 정보")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "postComment", orphanRemoval = true)
    @JsonIgnore
    @BatchSize(size = 100)
    @ApiModelProperty(value = "댓글 정보")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "postBookMark", orphanRemoval = true)
    @JsonIgnore
    @BatchSize(size = 100)
    @ApiModelProperty(value = "북마크 정보")
    private List<BookMark> bookMarks = new ArrayList<>();



}
