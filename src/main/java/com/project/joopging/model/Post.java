package com.project.joopging.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.dto.post.PostDetailResponseDto;
import com.project.joopging.dto.post.PostUpdateRequestDto;
import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "게시글 정보")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "게시글 아이디")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 제목")
    private String title;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 내용")
    private String content;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 러닝 시작일")
    private LocalDate runningDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 모집 시작일")
    private LocalDate startDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "게시글 모집 마감일")
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "게시글 지역")
    private Location location;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "게시글 지형")
    private Type type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "게시글 거리")
    private Distance distance;

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
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    private User writer;

    @OneToMany(mappedBy = "postJoin")
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "참가자 정보")
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "postReview")
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "후기 정보")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "postComment")
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "댓글 정보")
    private List<Comment> comments = new ArrayList<>();


    public Post(PostCreateRequestDto requestDto,User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.runningDate = requestDto.getRunningDate();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.location = Location.getLocationById(requestDto.getLocation());
        this.type= Type.getTypeById(requestDto.getType());
        this.distance = Distance.getDistanceById(requestDto.getDistance());
        this.limitPeople = requestDto.getLimitPeople();
        this.postImg = requestDto.getPostImg();
        this.writer = user;
    }

    public static Post of(PostCreateRequestDto requestDto, User user) {
        return new Post(requestDto,user);
    }

    public boolean isWrittenBy(User user) {
        return this.writer.getId().equals(user.getId());
    }

    public void update(PostUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.runningDate = requestDto.getRunningDate();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.location = Location.getLocationById(requestDto.getLocation());
        this.type= Type.getTypeById(requestDto.getType());
        this.distance = Distance.getDistanceById(requestDto.getDistance());
        this.limitPeople = requestDto.getLimitPeople();
        this.nowPeople = requestDto.getNowPeople();
        this.postImg = requestDto.getPostImg();
    }
        //댓글 추가하기

    public PostDetailResponseDto toBuildDetailPost(UserDetails userDetails) {

        if(userDetails == null) {
            return PostDetailResponseDto.builder()
                    .postId(this.id)
                    .title(this.title)
                    .content(this.content)
                    .runningDate(this.runningDate)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .location(location.getName())
                    .type(type.getName())
                    .distance(distance.getName())
                    .limitPeople(this.limitPeople)
                    .nowPeople(this.nowPeople)
                    .postImg(this.postImg)
                    .viewCount(this.viewCount)
                    .commentList(this.comments)
                    .build();
        } else {
            return PostDetailResponseDto.builder()
                    .postId(this.id)
                    .title(this.title)
                    .content(this.content)
                    .runningDate(this.runningDate)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .location(location.getName())
                    .type(type.getName())
                    .distance(distance.getName())
                    .limitPeople(this.limitPeople)
                    .nowPeople(this.nowPeople)
                    .postImg(this.postImg)
                    .viewCount(this.viewCount)
                    .commentList(this.comments)
                    .build();
        }
    }

    public void plusNowPeople() {
        this.nowPeople += 1;
    }

    public void minusNowPeople() {
        this.nowPeople -= 1;
    }
}
