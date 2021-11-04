package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Crew;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.CrewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Api(tags = "Party Controller Api V1")
public class CrewController {

    private final CrewService crewService;

    //모임 참여하기 api

    @ApiOperation(value = "모임 참여하기")
    @PostMapping("/posts/{post_id}/crews")
    public ResponseDto join(
            @ApiParam(value = "게시글 ID") @PathVariable("post_id") Long postId,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        Long userId = userDetails.getUser().getId();
        System.out.println("userId = " + userId);
        System.out.println(postId);
        Crew crew = crewService.join(postId, userId);
        return new ResponseDto(201L, "모임에 참여하였습니다.", "");
    }

    //모임 참여 취소하기 api
    @ApiOperation(value = "모임 참여 취소하기")
    @DeleteMapping("/posts/{post_id}/crews")
    public ResponseDto cancelJoin(
            @ApiParam(value = "게시글 ID") @PathVariable("post_id") Long postId,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        Long userId = userDetails.getUser().getId();
        crewService.cancelJoin(postId, userId);
        return new ResponseDto(204L, "모임 참여를 취소했습니다.", "");
    }

    //로그인 상태 확인
    @ApiOperation(value = "로그인 체크")
    private void checkLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new CustomErrorException("로그인이 필요합니다.");
        }
    }
}
