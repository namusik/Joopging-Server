package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.post.PostMainPageResponseDto;
import com.project.joopging.dto.review.AllReviewResponseDto;
import com.project.joopging.model.Post;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.MainPageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "Main Controller Api V1")
public class MainController {

    private final MainPageService mainPageService;

    //메인페이지 리스트 보내주는 api
    @GetMapping("/main")
    public ResponseDto mainPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        HashMap<String, Object> resultList = new HashMap<>();

        //비로그인 로그인 공통 : 조회수 top5
        List<PostMainPageResponseDto> hotPlaceList = mainPageService.getByHotPlace();
        resultList.put("hot", hotPlaceList);

        //비로그인 로그인 공통 : 남은 모집인원 적은 순 5개
        List<PostMainPageResponseDto> closeSoonList = mainPageService.getByCloseSoon();
        resultList.put("close", closeSoonList);

        //비로그인 로그인 공통 : 최신 리뷰 5개
        List<AllReviewResponseDto> reviewList = mainPageService.getReviews();
        resultList.put("reviews", reviewList);
        
        if (userDetails == null) { //비로그인 시
            //전체 모임 달리는 날짜 최신순 5개
            List<PostMainPageResponseDto> recentPost = mainPageService.getByRecentPost();
            resultList.put("recent", recentPost);
            return new ResponseDto(200L, "ok", resultList);
        } else { //로그인 시
            //유저 지역기준 최신순 5개
            List<PostMainPageResponseDto> locationList = mainPageService.getByUserLocation(userDetails.getUser().getLocation());
            resultList.put("location", locationList);

            //유저 거리기준 최신순 5개
            List<PostMainPageResponseDto> distanceList = mainPageService.getByUserDistance(userDetails.getUser().getDistance());
            resultList.put("distance", distanceList);

            //유저 타입기준 최신순 5개
            List<PostMainPageResponseDto> typeList = mainPageService.getByUserType(userDetails.getUser().getType());
            resultList.put("type", typeList);

            return new ResponseDto(200L, "ok", resultList);
        }
    }
}
