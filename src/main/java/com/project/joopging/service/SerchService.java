package com.project.joopging.service;

import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.model.Post;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SerchService {
    private final PostRepository postRepository;

    public SerchService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findUseByFilter(Integer distance, Integer type, Integer[] location, UserDetailsImpl userDetails) {
        return postRepository.findByDistanceAndTypeAndLocation(distance, type, location[0]);

    }

//    public static void main(String[] args) {
//        Post post = new Post();
//        LocalDate date = LocalDate.now();
//        //더미데이터 생성  부분
//        PostCreateRequestDto requestDto = new PostCreateRequestDto(
//                "test1", "테스트입니다", date, date, date, 1, 2, 3, 5, ""
//        );
//
//        System.out.println("requestDto = " + requestDto);
//
//    }
}
