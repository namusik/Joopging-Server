package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.service.PostService;
import com.project.joopging.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    }

}
