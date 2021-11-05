package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
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

@RequiredArgsConstructor
@RestController
@Api(tags = "ReComment Controller Api V1")
public class ReCommentController {

//    @ApiOperation(value = "대댓글 추가")
//    @PostMapping("/recomments")
//    public ResponseDto createReComment(
//            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
//            @ApiParam @RequestBody
//            ) {}
}
