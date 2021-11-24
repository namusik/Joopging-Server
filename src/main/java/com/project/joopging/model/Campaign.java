package com.project.joopging.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.campaign.CampaignCreateRequestDto;
import com.project.joopging.dto.campaign.CampaignDetailResponseDto;
import com.project.joopging.dto.campaign.CampaignUpdateRequestDto;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@ApiModel(value = "캠페인 정보")
public class Campaign extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "캠페인 아이디")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 제목")
    private String title;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 모임장 소개")
    private String crewHeadIntro;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 내용")
    private String content;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 러닝 시작일")
    private LocalDateTime runningDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 모집 시작일")
    private LocalDate startDate;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 모집 마감일")
    private LocalDate endDate;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "캠페인 지역")
    private String location;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "캠페인 지형")
    private String type;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "캠페인 거리")
    private String distance;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 최대 인원수")
    private int limitPeople;

    @Column(nullable = false)
    @ApiModelProperty(value = "캠페인 현재 인원수")
    private int nowPeople = 1;

    @Column
    @ApiModelProperty(value = "캠페인 이미지")
    private String postImg;

    @Column
    @ApiModelProperty(value = "캠페인 조회수")
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    @ApiModelProperty(value = "캠페인 관리자 정보")
    private User admin;

    @OneToMany(mappedBy = "campaignJoin", orphanRemoval = true)
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "참가자 정보")
    private List<Crew> Crew = new ArrayList<>();

    @OneToMany(mappedBy = "campaignBookMark", orphanRemoval = true)
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "북마크 정보")
    private List<BookMark> bookMarks = new ArrayList<>();

    @Basic(fetch = FetchType.LAZY)
    @Formula("(select count(1) from book_mark bm where bm.campaign_id = id)")
    private Integer totalBookMarkCount;


    //게시글 작성
    public Campaign(CampaignCreateRequestDto requestDto, User user) {
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
        this.admin = user;
    }


    public static Campaign of(CampaignCreateRequestDto requestDto, User user) {
        return new Campaign(requestDto, user);
    }

    public boolean isWrittenBy(User user) {
        return this.admin.getId().equals(user.getId());
    }

    public void update(CampaignUpdateRequestDto updateRequestDto) {
        this.title = updateRequestDto.getTitle();
        this.content = updateRequestDto.getContent();
        this.crewHeadIntro = updateRequestDto.getCrewHeadIntro();
        this.runningDate = updateRequestDto.getRunningDate();
        this.startDate = updateRequestDto.getStartDate();
        this.endDate = updateRequestDto.getEndDate();
        this.location = updateRequestDto.getLocation();
        this.type= updateRequestDto.getType();
        this.distance = updateRequestDto.getDistance();
        this.limitPeople = updateRequestDto.getLimitPeople();
        this.postImg = updateRequestDto.getPostImg();
    }

    public CampaignDetailResponseDto toBuildDetailCampaign(UserDetailsImpl userDetails,
                                                           boolean joinCheck,
                                                           boolean bookMarkInfo,
                                                           String runningDateToString) {
        if(userDetails == null) {
            return CampaignDetailResponseDto.builder()
                    .campaignId(this.id)
                    .title(this.title)
                    .crewHeadIntro(this.crewHeadIntro)
                    .content(this.content)
                    .runningDate(runningDateToString)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .dDay(ChronoUnit.DAYS.between(this.getStartDate(), this.getEndDate()))
                    .location(this.location)
                    .type(this.type)
                    .distance(this.distance)
                    .limitPeople(this.limitPeople)
                    .nowPeople(this.nowPeople)
                    .campaignImg(this.postImg)
                    .viewCount(this.viewCount)
                    .totalBookMarkCount(this.totalBookMarkCount)
                    .adminName(this.admin.getNickname())
                    .adminImg(this.admin.getUserImg())
                    .intro(this.admin.getIntro())
                    .bookMarkInfo(bookMarkInfo)
                    .joinCheck(joinCheck)
                    .build();
        } else {
            return CampaignDetailResponseDto.builder()
                    .campaignId(this.id)
                    .title(this.title)
                    .crewHeadIntro(this.crewHeadIntro)
                    .content(this.content)
                    .runningDate(runningDateToString)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .location(this.location)
                    .type(this.type)
                    .distance(this.distance)
                    .dDay(ChronoUnit.DAYS.between(this.getStartDate(), this.getEndDate()))
                    .limitPeople(this.limitPeople)
                    .nowPeople(this.nowPeople)
                    .campaignImg(this.postImg)
                    .viewCount(this.viewCount)
                    .totalBookMarkCount(this.totalBookMarkCount)
                    .adminName(this.admin.getNickname())
                    .adminImg(this.admin.getUserImg())
                    .intro(this.admin.getIntro())
                    .bookMarkInfo(bookMarkInfo)
                    .joinCheck(joinCheck)
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
