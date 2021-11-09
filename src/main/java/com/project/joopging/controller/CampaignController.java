package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.campaign.CampaignCreateRequestDto;
import com.project.joopging.dto.campaign.CampaignDetailResponseDto;
import com.project.joopging.dto.campaign.CampaignUpdateRequestDto;
import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.model.Campaign;
import com.project.joopging.model.User;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.CampaignService;
import com.project.joopging.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Api(tags = "Campaign Controller Api V1")
public class CampaignController {

    private final UserService userService;
    private final CampaignService campaignService;

    @ApiOperation(value = "캠페인 만들기")
    @PostMapping("/campaign")
    public ResponseDto createCampaign(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "캠페인 생성 정보", required = true) @RequestBody CampaignCreateRequestDto requestDto
    ) {
        User user = userService.userFromUserDetails(userDetails);
        //관리자 권한 있는지 체크
        userService.userCheckEnumRole(user);

        campaignService.createCampaign(requestDto, user);
        return new ResponseDto(201L, "캠페인을 만들었습니다.", "");
    }

    @ApiOperation(value = "캠페인 삭제")
    @DeleteMapping("/campaign/{campaign_id}")
    public ResponseDto deleteCampaign(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "캠페인 ID", required = true) @PathVariable("campaign_id") Long campaignId
    ) {
        User user = userService.userFromUserDetails(userDetails);
        //관리자 권한 있는지 체크
        userService.userCheckEnumRole(user);

        campaignService.deleteCampaign(campaignId, user);
        return new ResponseDto(204L, "캠페인 삭제에 성공하였습니다.", "");
    }

    @ApiOperation(value = "캠페인 수정")
    @PutMapping("/campaign/{campaign_id}")
    public ResponseDto updateCampaign(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @ApiParam(value = "캠페인 ID", required = true) @PathVariable("campaign_id") Long campaignId,
            @ApiParam(value = "캠페인 업데이트 정보", required = true) @RequestBody CampaignUpdateRequestDto campaignUpdateRequestDto
    ) {
        User user = userService.userFromUserDetails(userDetails);
        //관리자 권한 있는지 체크
        userService.userCheckEnumRole(user);

        campaignService.updateCampaign(campaignId, campaignUpdateRequestDto, user);

        return new ResponseDto(200L, "캠페인 수정에 성공하였습니다. ", "");
    }

    @ApiOperation(value = "캠페인 상세 페이지")
    @GetMapping("/campaign/{campaign_id}")
    public ResponseDto getDetailCampaign(
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ApiParam(value = "캠페인 ID", required = true) @PathVariable Long campaign_id
    ) {
        Campaign campaign = campaignService.getDetailCampaignId(campaign_id);
        CampaignDetailResponseDto data = campaignService.toSetCampaignDetailResponseDto(campaign, userDetails);

        return new ResponseDto(200L, "캠페인 상세 페이지를 불러왔습니다 .", data);
    }
}
