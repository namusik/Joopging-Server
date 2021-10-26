package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.review.ReviewRequestDto;
import com.project.joopging.dto.review.ReviewResponseDto;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.ReviewService;
import com.project.joopging.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final UserService userService;
    private final ReviewService reviewService;

    
    //후기 작성
    @PostMapping("/reviews/{post_id}")
    public ResponseDto createReview(@PathVariable("post_id") Long postId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Review review = reviewService.createReview(postId, requestDto, userDetails);

        return new ResponseDto(200L, "후기를 저장했습니다.", "");
    }


    //후기 수정
    @PutMapping("/reviews/{review_id}")
    public ResponseDto editReview(@PathVariable("review_id") Long reviewId, @RequestBody ReviewRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.userFromUserDetails(userDetails);
        Review review = reviewService.editReview(reviewId, requestDto);
        return new ResponseDto(200L, "후기를 수정했습니다.", "");
    }

    //후기 삭제
    @DeleteMapping("/reviews/{review_id}")
    public ResponseDto deleteReivew(@PathVariable("review_id") Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.userFromUserDetails(userDetails);
        reviewService.deleteReview(reviewId, userDetails);
        return new ResponseDto(200L, "후기를 삭제했습니다.", "");
    }

    //전체 후기 보여주기
    @GetMapping("/reviews")
    public ResponseDto showAllReview(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ReviewResponseDto> reviewList = reviewService.showAllReview();
        return new ResponseDto(200L, "전체 후기를 불러왔습니다", reviewList);
    }

}
