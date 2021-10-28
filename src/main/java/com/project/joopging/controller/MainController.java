package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.post.PostMainPageResponseDto;
import com.project.joopging.model.Post;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainPageService mainPageService;

    //메인페이지 리스트 보내주는 api
    @GetMapping("/main")
    public ResponseDto mainPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        HashMap<String, Object> allList = new HashMap<>();

        //비로그인 로그인 공통 : 조회수 top5
        List<PostMainPageResponseDto> hotPlaceList = mainPageService.getByHotPlace();
        allList.put("hot", hotPlaceList);
        if (userDetails == null) { //비로그인 시

            return new ResponseDto(200L, "ok", allList);
        } else { //로그인 시
            //유저 지역기준 최신순 5개
//            List<PostMainPageResponseDto> locationList = mainPageService.getByUserLocation(userDetails.getUser().getLocation());

            //유저 거리기준 최신순 5개
//            List<PostMainPageResponseDto> distanceList = mainPageService.getByUserDistance(userDetails.getUser().getDistance());

            //유저 타입기준 최신순 5개
//            List<PostMainPageResponseDto> typeList = mainPageService.getByUserType(userDetails.getUser().getType());

            return new ResponseDto(200L, "ok", allList);
        }
    }
}
