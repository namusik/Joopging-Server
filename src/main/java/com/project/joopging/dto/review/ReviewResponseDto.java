package com.project.joopging.dto.review;

import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private String content;

    private String reviewImg;

    private Post post;

    private User user;
}
