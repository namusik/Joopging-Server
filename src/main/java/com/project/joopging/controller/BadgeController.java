package com.project.joopging.controller;


import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.badge.MyBadgeListResponseDto;
import com.project.joopging.model.User;
import com.project.joopging.service.BadgeService;
import com.project.joopging.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "Badge Controller Api V1")
public class BadgeController {

    private final UserService userService;
    private final BadgeService badgeService;


    @ApiOperation(value = "마이페이지 뱃지")
    @GetMapping("/badges")
    public ResponseDto myBadges(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.userFromUserDetails(userDetails);
        List<MyBadgeListResponseDto> data = badgeService.getMyBadgeListByUser(user);
        return new ResponseDto(200L,"내 뱃지 불러오기 성공", data);
    }
}
