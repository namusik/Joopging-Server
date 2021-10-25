package com.project.joopging.service;

import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.dto.post.PostUpdateRequestDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    //게시글 만들기
    public void createPost(PostCreateRequestDto requestDto, User user) {
        Post post = Post.of(requestDto,user);
        postRepository.save(post);
    }

    //게시글 업데이트
    public void updatePost(Long postId, PostUpdateRequestDto requestDto, User user) {
        Post post = getPostById(postId);
        if (post.isWrittenBy(user)) {
            post.update(requestDto);
        } else {
            throw new CustomErrorException("모임의 작성자가 아닙니다");
        }
    }

    //게시글 삭제
    public void deletePost(Long postId, User user) {
        Post post = getPostById(postId);
        if(post.isWrittenBy(user)) {
            postRepository.delete(post);
        } else {
            throw new CustomErrorException("모임의 작성자가 아닙니다");
        }
    }

    public Post getDetailPostById(Long postId) {
        Post post = getPostById(postId);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
        return post;
    }




    private Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("게시글을 찾을 수 없습니다.")
        );
    }



}
