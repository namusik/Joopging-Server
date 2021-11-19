package com.project.joopging.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.comment.AllCommentResponseDto;
import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.dto.post.PostDetailResponseDto;
import com.project.joopging.dto.post.PostUpdateRequestDto;
import com.project.joopging.dto.user.MyApplicationPostListResponseDto;
import com.project.joopging.dto.user.MyBookmarkListResponseDto;
import com.project.joopging.dto.user.MyPostPageListResponseDto;
import com.project.joopging.security.UserDetailsImpl;
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

    @Column
    @ApiModelProperty(value = "출석관리 완료여부")
    private boolean postAttendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    private User writer;

    @OneToMany(mappedBy = "postJoin", orphanRemoval = true)
    @JsonIgnore
    @BatchSize(size = 100)
    @ApiModelProperty(value = "참가자 정보")
    private List<Crew> Crew = new ArrayList<>();

    @OneToMany(mappedBy = "postReview", orphanRemoval = true)
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


    //게시글 작성
    public Post(PostCreateRequestDto requestDto,User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.crewHeadIntro = requestDto.getCrewHeadIntro();
        this.runningDate = requestDto.getRunningDate();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.location = requestDto.getLocation();
        this.type= requestDto.getType();
        this.distance = requestDto.getDistance();
        this.limitPeople = requestDto.getLimitPeople();
        this.postImg = requestDto.getPostImg();
        this.writer = user;
        this.postAttendation = false;
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
        this.crewHeadIntro = requestDto.getCrewHeadIntro();
        this.endDate = requestDto.getEndDate();
        this.location = requestDto.getLocation();
        this.type= requestDto.getType();
        this.distance = requestDto.getDistance();
        this.postImg = requestDto.getPostImg();
    }


    public PostDetailResponseDto toBuildDetailPost(UserDetailsImpl userDetails,
                                                   boolean joinCheck,
                                                   boolean bookMarkInfo,
                                                   String runningDateToString) {

        if(userDetails == null) {
            return PostDetailResponseDto.builder()
                    .postId(this.id)
                    .title(this.title)
                    .crewHeadIntro(this.crewHeadIntro)
                    .content(this.content)
                    .runningDate(runningDateToString)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .dDay(ChronoUnit.DAYS.between(LocalDate.now(), this.getEndDate()))
                    .location(this.location)
                    .type(this.type)
                    .distance(this.distance)
                    .limitPeople(this.limitPeople)
                    .nowPeople(this.nowPeople)
                    .postImg(this.postImg)
                    .viewCount(this.viewCount)
                    .totalBookMarkCount(this.totalBookMarkCount)
                    .writerName(this.writer.getNickname())
                    .userImg(this.writer.getUserImg())
                    .intro(this.writer.getIntro())
                    .joinCheck(joinCheck)
                    .bookMarkInfo(bookMarkInfo)
                    .build();
        } else {
            return PostDetailResponseDto.builder()
                    .postId(this.id)
                    .title(this.title)
                    .crewHeadIntro(this.crewHeadIntro)
                    .content(this.content)
                    .runningDate(runningDateToString)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .location(this.location)
                    .type(this.type)
                    .distance(this.distance)
                    .dDay(ChronoUnit.DAYS.between(LocalDate.now(), this.getEndDate()))
                    .limitPeople(this.limitPeople)
                    .nowPeople(this.nowPeople)
                    .postImg(this.postImg)
                    .viewCount(this.viewCount)
                    .totalBookMarkCount(this.totalBookMarkCount)
                    .writerName(this.writer.getNickname())
                    .userImg(this.writer.getUserImg())
                    .intro(this.writer.getIntro())
                    .joinCheck(joinCheck)
                    .bookMarkInfo(bookMarkInfo)
                    .build();
        }
    }






    public void plusNowPeople() {
        this.nowPeople += 1;
    }

    public void minusNowPeople() {
        this.nowPeople -= 1;
    }


    public MyApplicationPostListResponseDto toBuildMyApplicationPost(boolean bookMarkInfo,
                                                                     String runningDateToString,
                                                                     boolean attendation) {
        return MyApplicationPostListResponseDto.builder()
                .postId(this.id)
                .title(this.title)
                .content(this.content)
                .runningDate(runningDateToString)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .location(this.location)
                .type(this.type)
                .distance(this.distance)
                .dDay(ChronoUnit.DAYS.between(LocalDate.now(), this.getEndDate()))
                .limitPeople(this.limitPeople)
                .nowPeople(this.nowPeople)
                .postImg(this.postImg)
                .viewCount(this.viewCount)
                .bookMarkCount(this.totalBookMarkCount)
                .writerName(this.writer.getNickname())
                .userImg(this.writer.getUserImg())
                .intro(this.writer.getIntro())
                .bookMarkInfo(bookMarkInfo)
                .attendation(attendation)
                .build();
    }

    public MyPostPageListResponseDto toBuildMyCreatePost(String runningDateToString) {
        return MyPostPageListResponseDto.builder()
                .postId(this.id)
                .title(this.title)
                .content(this.content)
                .runningDate(runningDateToString)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .location(this.location)
                .type(this.type)
                .distance(this.distance)
                .dDay(ChronoUnit.DAYS.between(LocalDate.now(), this.getEndDate()))
                .limitPeople(this.limitPeople)
                .nowPeople(this.nowPeople)
                .postImg(this.postImg)
                .viewCount(this.viewCount)
                .bookMarkCount(this.totalBookMarkCount)
                .writerName(this.writer.getNickname())
                .userImg(this.writer.getUserImg())
                .intro(this.writer.getIntro())
                .postAttendation(this.postAttendation)
                .build();
    }

    public MyBookmarkListResponseDto toBuildMyBookmarkPost(boolean joinCheck, String runningDateToString) {
        return MyBookmarkListResponseDto.builder()
                .postId(this.id)
                .title(this.title)
                .content(this.content)
                .runningDate(runningDateToString)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .location(this.location)
                .type(this.type)
                .distance(this.distance)
                .dDay(ChronoUnit.DAYS.between(LocalDate.now(), this.getEndDate()))
                .limitPeople(this.limitPeople)
                .nowPeople(this.nowPeople)
                .postImg(this.postImg)
                .viewCount(this.viewCount)
                .bookMarkCount(this.totalBookMarkCount)
                .writerName(this.writer.getNickname())
                .userImg(this.writer.getUserImg())
                .intro(this.writer.getIntro())
                .joinCheck(joinCheck)
                .build();
    }

    public void attend() {
        this.postAttendation = true;
    }
}
