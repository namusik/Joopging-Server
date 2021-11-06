package com.project.joopging.service;

import com.project.joopging.dto.post.PostMainPageResponseDto;
import com.project.joopging.dto.review.AllReviewResponseDto;
import com.project.joopging.model.BookMark;
import com.project.joopging.model.Post;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import com.project.joopging.repository.BookMarkRepository;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.repository.ReviewRepository;
import com.project.joopging.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final BookMarkRepository bookMarkRepository;
    
    //조회수 높은거 5개
    public List<PostMainPageResponseDto> getByHotPlace(UserDetailsImpl userDetails) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> result  = postRepository.findAllByOrderByViewCountDesc(pageable).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : result) {
            User writer = post.getWriter();
            boolean checkBookMark = checkBookMark(userDetails.getUser(), post);
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer, checkBookMark);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
    
    //최신리뷰 5개
    public List<AllReviewResponseDto> getReviews(UserDetailsImpl userDetails) {
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

    //최근 작성 순
    public List<PostMainPageResponseDto> getByRecentPost(UserDetailsImpl userDetails) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> result  = postRepository.findAllByOrderByCreatedAtDesc(pageable).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : result) {
            User writer = post.getWriter();
            boolean checkBookMark = checkBookMark(userDetails.getUser(), post);
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer, checkBookMark);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
    //Dday 기준
    public List<PostMainPageResponseDto> getByCloseSoon(UserDetailsImpl userDetails) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> result  = postRepository.findAllByOrderByEndDateAsc(pageable).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : result) {
            User writer = post.getWriter();
            boolean checkBookMark = checkBookMark(userDetails.getUser(), post);
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer, checkBookMark);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }

    //유저 지역 기반 5개
    public List<PostMainPageResponseDto> getByUserLocation(String location) {
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
    public List<PostMainPageResponseDto> getByUserDistance(String distance) {
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
    public List<PostMainPageResponseDto> getByUserType(String type) {
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

    public boolean checkBookMark(User user, Post post) {
        Optional<BookMark> BookMark = bookMarkRepository.findByUserBookMarkAndPostBookMark(user, post);
        if (BookMark.isPresent()) {
            return true
        } else {
            return false;
        }
    }
}
