package com.project.joopging.service;

import com.project.joopging.dto.post.PostMainPageResponseDto;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.PostRepository;
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
    
    //조회수 높은거 5개
    public List<PostMainPageResponseDto> getByHotPlace() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Post> content  = postRepository.findAllByOrderByViewCountDesc(pageable).getContent();
        List<PostMainPageResponseDto> postList = new ArrayList<>();
        for (Post post : content) {
            User writer = post.getWriter();
            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
            postList.add(postMainPageResponseDto);
        }
        return postList;
    }
    
    //유저 지역 기반 5개
//    public List<PostMainPageResponseDto> getByUserLocation(String location) {
//        Pageable pageable = PageRequest.of(0, 5);
//        List<Post> content  = postRepository.findAllByLocationOrderByRunnigDateDesc(pageable,location).getContent();
//        List<PostMainPageResponseDto> postList = new ArrayList<>();
//        for (Post post : content) {
//            User writer = post.getWriter();
//            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
//            postList.add(postMainPageResponseDto);
//        }
//        return postList;
//    }
//
//    public List<PostMainPageResponseDto> getByUserDistance(String distance) {
//        Pageable pageable = PageRequest.of(0, 5);
//        List<Post> content  = postRepository.findAllByLocationOrderByRunnigDateDesc(pageable,distance).getContent();
//        List<PostMainPageResponseDto> postList = new ArrayList<>();
//        for (Post post : content) {
//            User writer = post.getWriter();
//            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
//            postList.add(postMainPageResponseDto);
//        }
//        return postList;
//    }
//
//    public List<PostMainPageResponseDto> getByUserType(String type) {
//        Pageable pageable = PageRequest.of(0, 5);
//        List<Post> content  = postRepository.findAllByLocationOrderByRunnigDateDesc(pageable,type).getContent();
//        List<PostMainPageResponseDto> postList = new ArrayList<>();
//        for (Post post : content) {
//            User writer = post.getWriter();
//            PostMainPageResponseDto postMainPageResponseDto = new PostMainPageResponseDto(post, writer);
//            postList.add(postMainPageResponseDto);
//        }
//        return postList;
//    }
}
