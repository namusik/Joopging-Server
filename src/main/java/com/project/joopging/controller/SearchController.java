package com.project.joopging.controller;


import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.post.PostSearchesDto;
import com.project.joopging.model.Post;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.SerchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SerchService serchService;

    @GetMapping("/searches")
    public ResponseDto findUseByFilter(
            @RequestParam Integer distance,
            @RequestParam Integer type,
            @RequestParam Integer[] location,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<PostSearchesDto> post = serchService.findUseByFilter(distance, type, location,userDetails);

        return new ResponseDto(200L, "성공", post);
    }
}
