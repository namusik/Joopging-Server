package com.project.joopging.service;

import com.project.joopging.dto.review.AllReviewResponseDto;
import com.project.joopging.dto.review.DetailReviewResponseDto;
import com.project.joopging.dto.review.ReviewRequestDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Post;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.repository.ReviewRepository;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    
    //후기 작성
    @Transactional
    public Review createReview(Long postId, ReviewRequestDto requestDto, UserDetailsImpl userDetails) {
        //해당 모임정보 가져오기
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 모임입니다")
        );

        //로그인 유저 정보 가져오기
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new CustomErrorException("로그인 후 사용가능합니다")
        );

        Review review = new Review(requestDto, post, user);

        post.getReviews().add(review);
        user.getReview().add(review);
        reviewRepository.save(review);
        return review;
    }
    
    //후기 수정
    @Transactional
    public Review editReview(Long reviewId, ReviewRequestDto requestDto) {
        //해당 리뷰 가져오기
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 후기입니다")
        );

        review.update(requestDto);

        return review;
    }
    
    //후기 삭제
    @Transactional
    public void deleteReview(Long reviewId, UserDetailsImpl userDetails) {

        reviewRepository.deleteById(reviewId);
    }
    
    //전체 후기 불러오기
    //추후 페이징 처리 관련 정하고 수정 필요
    public List<AllReviewResponseDto> showAllReview() {
        List<Review> reviewList = reviewRepository.findAll();
        List<AllReviewResponseDto> reviewResponseDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            User user = review.getUserReview();
            AllReviewResponseDto reviewResponseDto = new AllReviewResponseDto(review, user);
            reviewResponseDtoList.add(reviewResponseDto);
        }
        return reviewResponseDtoList;
    }

    public DetailReviewResponseDto showOneReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 리뷰입니다")
        );
        User user = review.getUserReview();
        Post post = review.getPostReview();
        return new DetailReviewResponseDto(review, user, post);
    }

    public List<AllReviewResponseDto> getMyReviews(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 사용자입니다.")
        );
        List<Review> result = user.getReview();
        List<AllReviewResponseDto> reviewResponseDtoList = new ArrayList<>();
        for (Review review : result) {
            AllReviewResponseDto reviewResponseDto = new AllReviewResponseDto(review, user);
            reviewResponseDtoList.add(reviewResponseDto);
        }
        return reviewResponseDtoList;
    }

    //
}
