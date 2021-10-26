package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.model.Party;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    //모임 참여하기 api
    @PostMapping("/posts/join/{post_id}")
    public ResponseDto join(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        System.out.println("userId = " + userId);
        System.out.println(postId);
        Party party = partyService.join(postId, userId);
        return new ResponseDto(200L, "모임에 참여하였습니다.", "");
    }

    //모임 참여 취소하기 api
    @DeleteMapping("/posts/join/{post_id}")
    public ResponseDto cancelJoin(@PathVariable("post_id") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        partyService.cancleJoin(postId, userId);
        return new ResponseDto(200L, "모임 참여를 취소했습니다.", "");
    }
}
