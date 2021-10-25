package com.project.joopging.service;

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

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

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
}
