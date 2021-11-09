package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.crew.CrewReponseDto;
import com.project.joopging.model.Crew;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.CrewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;


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
        Long userId = userDetails.getUser().getId();
        crewService.cancelJoin(postId, userId);
        return new ResponseDto(204L, "모임 참여를 취소했습니다.", "");
    }

    //모임 참여 인원 불러오기
//    @ApiOperation(value = "모임 참여 인원 불러오기")
//    @GetMapping("/posts/{post_id}/my")
//    public ResponseDto getCrews(@ApiParam(value = "모임 ID") @PathVariable("post_id") Long postId) {
//        List<CrewReponseDto> result = crewService.getCrews(postId);
//        return new ResponseDto(200L, "참여자들을 가져왔습니다", result);
//    }

}
