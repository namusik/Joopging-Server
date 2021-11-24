//package com.project.joopging.controller;
//
//import com.project.joopging.dto.ResponseDto;
//import com.project.joopging.dto.reCommentDto.ReCommentCreateRequestDto;
//import com.project.joopging.dto.reCommentDto.ReCommentUpdateRequestDto;
//import com.project.joopging.model.User;
//import com.project.joopging.service.ReCommentService;
//import com.project.joopging.service.UserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//@RequiredArgsConstructor
//@RestController
//@Api(tags = "ReComment Controller Api V1")
//public class ReCommentController {
//
//    private final ReCommentService reCommentService;
//    private final UserService userService;
//
//    @ApiOperation(value = "대댓글 추가")
//    @PostMapping("/recomments")
//    public ResponseDto createReComment(
//            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
//            @ApiParam(value = "대댓글 객체", required = true) @RequestBody ReCommentCreateRequestDto requestDto
//    ) {
//        User user = userService.userFromUserDetails(userDetails);
//        reCommentService.createReComment(user,requestDto);
//        return new ResponseDto(201L,"댓글 작성이 완료 되었습니다", "");
//    }
//
//    @ApiOperation(value = "대댓글 수정")
//    @PutMapping("/recomments")
//    public ResponseDto updateReComment(
//            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
//            @ApiParam(value = "대댓글 수정정보", required = true) @RequestBody ReCommentUpdateRequestDto requestDto
//    ) {
//        User user =userService.userFromUserDetails(userDetails);
//        reCommentService.updateReComment(user,requestDto);
//        return new ResponseDto(200L,"댓글 수정이 완료 되었습니다","");
//    }
//
//    @ApiOperation(value = "대댓글 삭제")
//    @DeleteMapping("/recomments/{recomment_id}")
//    public ResponseDto deleteReComment(
//            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
//            @ApiParam(value = "대댓글 아이디",required = true) @PathVariable("recomment_id") Long reCommentId
//    ) {
//        User user = userService.userFromUserDetails(userDetails);
//        reCommentService.deleteReComment(user,reCommentId);
//        return new ResponseDto(200L,"댓글 삭제가 완료되었습니다.","");
//    }
//}
