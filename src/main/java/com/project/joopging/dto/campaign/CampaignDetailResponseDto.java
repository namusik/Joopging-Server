package com.project.joopging.dto.campaign;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@Builder
public class CampaignDetailResponseDto {
    //캠페인 정보
    private final Long campaignId;
    private final String title;
    private final String content;
    private final String crewHeadIntro;
    private final String runningDate;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long dDay;
    private final String location;
    private final String type;
    private final String distance;
    private final int limitPeople;
    private final int nowPeople;
    private final String campaignImg;
    private final Integer viewCount;
    private final Integer totalBookMarkCount;


    //작성자 정보
    private final String adminName;
    private final String adminImg;
    private final String intro;
    private final boolean joinCheck;
    private final boolean bookMarkInfo;
}



