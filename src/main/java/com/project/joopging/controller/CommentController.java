package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.comment.CommentCreateRequestDto;
import com.project.joopging.dto.comment.CommentUpdateRequestDto;
import com.project.joopging.model.User;
import com.project.joopging.service.CommentService;
import com.project.joopging.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@Api(tags = "Comment Controller Api V1")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @ApiOperation(value = "댓글 추가")
    @PostMapping("/comments")
    public ResponseDto createComment(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetail,
            @ApiParam(value = "댓글 객체", required = true) @RequestBody CommentCreateRequestDto requestDto
    ) {
        User user = userService.userFromUserDetails(userDetail);
        commentService.createComment(user,requestDto);
        return new ResponseDto(201L,"댓글 작성이 완료되었습니다.","");
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/comments/{comment_id}")
    public ResponseDto updateComment(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "댓글 아이디", required = true) @PathVariable("comment_id") Long commentId,
            @ApiParam(value = "댓글 수정 정보", required = true) @RequestBody CommentUpdateRequestDto requestDto
            ) {
        User user = userService.userFromUserDetails(userDetails);
        commentService.update(user,requestDto,commentId);
        return new ResponseDto(200L,"댓글 수정이 완료되었습니다.","");
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/comments/{comment_id}")
    public ResponseDto deleteComment(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "댓글 아이디", required = true) @PathVariable("comment_id") Long commentId
    ) {
        User user = userService.userFromUserDetails(userDetails);
        commentService.delete(user,commentId);
        return new ResponseDto(204L,"댓글 삭제가 완료되었습니다.","");
    }

}
