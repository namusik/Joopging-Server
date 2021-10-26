package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.dto.post.PostDetailResponseDto;
import com.project.joopging.dto.post.PostUpdateRequestDto;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.service.PostService;
import com.project.joopging.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Api(tags = "Post Controller Api V1")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @ApiOperation(value = "게시글 만들기")
    @PostMapping("/posts")
    public ResponseDto createPost(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "게시글 생성 정보", required = true) @RequestBody PostCreateRequestDto requestDto
    ) {
        User user = userService.userFromUserDetails(userDetails);
        postService.createPost(requestDto,user);
        return new ResponseDto(200L,"모임을 만들었습니다.", "");
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("/posts/{post_id}")
    public ResponseDto updatePost(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "게시글 ID", required = true) @PathVariable("post_id") Long postId,
            @ApiParam(value = "게시글 업데이트 정보", required = true) @RequestBody PostUpdateRequestDto requestDto
    ) {
       User user = userService.userFromUserDetails(userDetails);
       postService.updatePost(postId,requestDto,user);
       return new ResponseDto(200L, "모임 수정에 성공하였습니다", "");
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/posts/{post_id}")
    public ResponseDto deletePost(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "게시글 ID", required = true) @PathVariable("post_id") Long postId
    ) {
        User user = userService.userFromUserDetails(userDetails);
        postService.deletePost(postId,user);
        return new ResponseDto(200L, "모임 삭제에 성공하였습니다.", "");
    }

    @ApiOperation(value = "게시글 상세페이지")
    @GetMapping("/posts/{post_id}")
    public ResponseDto getDetailPost(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "게시글 ID", required = true) @PathVariable("post_id") Long postId
    ) {
        Post post = postService.getDetailPostById(postId);
        PostDetailResponseDto data = postService.toSetPostDetailResponseDto(post,userDetails);
        return new ResponseDto(200L,"모임 상세페이지 불러오기에 성공하였습니다", data);


    }

}
