package com.project.joopging.dto.post;

import com.project.joopging.model.Comment;
import com.project.joopging.model.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostMainPageResponseDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime runningDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String type;
    private String distance;
    private int limitPeople;
    private int nowPeople;
    private String postImg;
    private Integer viewCount;
    private User writer;
}
