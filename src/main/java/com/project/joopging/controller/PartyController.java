package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Party;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.PartyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "Party Controller Api V1")
public class PartyController {

    private final PartyService partyService;

    //모임 참여하기 api
    @PostMapping("/posts/join/{post_id}")
    public ResponseDto join(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        Long userId = userDetails.getUser().getId();
        System.out.println("userId = " + userId);
        System.out.println(postId);
        Party party = partyService.join(postId, userId);
        return new ResponseDto(200L, "모임에 참여하였습니다.", "");
    }

    //모임 참여 취소하기 api
    @DeleteMapping("/posts/join/{post_id}")
    public ResponseDto cancelJoin(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        Long userId = userDetails.getUser().getId();
        partyService.cancleJoin(postId, userId);
        return new ResponseDto(200L, "모임 참여를 취소했습니다.", "");
    }

    //로그인 상태 확인
    private void checkLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new CustomErrorException("로그인 사용자만 사용가능한 기능입니다.");
        }
    }
}
