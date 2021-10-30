package com.project.joopging.service;

import com.project.joopging.dto.post.PostMainPageResponseDto;
import com.project.joopging.dto.review.AllReviewResponseDto;
import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.model.Post;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    
    //조회수 높은거 5개
    public List<PostMainPageResponseDto> getByHotPlace() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> result  = postRepository.findAllByOrderByViewCountDesc(pageable).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : result) {
            User writer = post.getWriter();
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
    
    //최신리뷰 5개
    public List<AllReviewResponseDto> getReviews() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Review> result = reviewRepository.findAllByOrderByModifiedAtDesc(pageable).getContent();
        List<AllReviewResponseDto> reviewList = new ArrayList<>();
        for (Review review : result) {
            User user = review.getUserReview();
            AllReviewResponseDto allReviewResponseDto = new AllReviewResponseDto(review, user);
            reviewList.add(allReviewResponseDto);
        }
        return reviewList;
    }

    //달리는 날짜 빠른순
    public List<PostMainPageResponseDto> getByRecentPost() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> result  = postRepository.findAllByOrderByRunningDateAsc(pageable).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : result) {
            User writer = post.getWriter();
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
    //인원 마감 적은순
    public List<PostMainPageResponseDto> getByCloseSoon() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> result  = postRepository.findByCloseSoon(pageable).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : result) {
            User writer = post.getWriter();
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }

    //유저 지역 기반 5개
    public List<PostMainPageResponseDto> getByUserLocation(Location location) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> content  = postRepository.findAllByLocationOrderByRunningDateAsc(pageable,location).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : content) {
            User writer = post.getWriter();
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
    //유저 거리 기반 5개
    public List<PostMainPageResponseDto> getByUserDistance(Distance distance) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> content  = postRepository.findAllByDistanceOrderByRunningDateAsc(pageable,distance).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : content) {
            User writer = post.getWriter();
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
    //유저 타입기반 5개
    public List<PostMainPageResponseDto> getByUserType(Type type) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> content  = postRepository.findAllByTypeOrderByRunningDateAsc(pageable,type).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : content) {
            User writer = post.getWriter();
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
}
