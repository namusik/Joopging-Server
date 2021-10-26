package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.model.Join;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    //모임 참여하기 api
    @PostMapping("/posts/join/{post_id}")
    public ResponseDto join(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Join join = joinService.join(postId, userId);
        return new ResponseDto(200L, "모임에 참여하였습니다.", "");
    }

    //모임 참여 취소하기 api
    @DeleteMapping("/posts/join/{post_id}")
    public ResponseDto cancelJoin(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        joinService.cancleJoin(postId, userId);
        return new ResponseDto(200L, "모임 참여를 취소했습니다.", "");
    }
}
