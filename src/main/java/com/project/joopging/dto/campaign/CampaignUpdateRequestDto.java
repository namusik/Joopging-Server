package com.project.joopging.dto.campaign;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CampaignUpdateRequestDto {
    private final String title;
    private final String crewHeadIntro;
    private final String content;
    private final LocalDateTime runningDate;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String location;
    private final String type;
    private final String distance;
    private final int limitPeople;
    private final String postImg;
}
