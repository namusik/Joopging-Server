package com.project.joopging.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PostCreateRequestDto {

    private final String title;
    private final String content;
    private final LocalDateTime runningDate;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer location;
    private final Integer type;
    private final Integer distance;
    private final int limitPeople;
    private final String postImg;

}
