package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.review.ReviewRequestDto;
import com.project.joopging.model.Review;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.ReviewService;
import com.project.joopging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final UserService userService;
    private final ReviewService reviewService;

    @PostMapping("/reviews/{post_id}")
    public ResponseDto createReview(@PathVariable("post_id") Long postId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Review review = reviewService.createReview(postId, requestDto, userDetails);

        return new ResponseDto(200L, "후기를 저장했습니다.", "");
    }
}
