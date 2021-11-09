package com.project.joopging.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewRequestDto {
    private Long postId;
    private String title;
    private String content;
    private String reviewImg;
    private int star;
    private int satiRate;
    private int levelRate;
    private int trashRate;
}
