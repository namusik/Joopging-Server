package com.project.joopging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.review.AllReviewResponseDto;
import com.project.joopging.dto.user.*;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.User;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.KakaoUserService;
import com.project.joopging.service.PostService;
import com.project.joopging.service.ReviewService;
import com.project.joopging.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "User Controller Api V1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoUserService kakaoUserService;
    private final PostService postService;
    private final ReviewService reviewService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/users")
    @ResponseBody
    public ResponseDto createUser(
            @ApiParam(value = "유저 생성정보", required = true) @RequestBody SignupRequestDto signupRequestDto) {
        if (userService.registerUser(signupRequestDto)) {
            String username = signupRequestDto.getNickname();
            log.info(username + "님 환영합니다 !");
            return new ResponseDto(201L, "회원가입에 성공하였습니다 !", "");
        }
        return new ResponseDto(500L, "회원가입에 실패하였습니다 ...", "");
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/users/login")
    @ResponseBody
    public ResponseDto login(
            @ApiParam(value = "유저 로그인 정보", required = true) @RequestBody LoginUserDto loginUserDto,
            @ApiIgnore HttpServletResponse response) {
        User user = userService.login(loginUserDto);
        String checkEmail = user.getEmail();

        String token = jwtTokenProvider.createToken(checkEmail);
        response.setHeader("X-AUTH-TOKEN", token);

        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        LoginDetailReponseDto loginDetailReponseDto = userService.toSetLoginDetailResponse(user);
        loginResponseDto.setUser(loginDetailReponseDto);
        loginResponseDto.setJwtToken(token);

        return new ResponseDto(200L, "로그인에 성공했습니다", loginResponseDto);
    }

    @ApiOperation(value = "유저 삭제")
    @DeleteMapping("/users")
    @ResponseBody
    public ResponseDto deleteUser(
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        String email1 = userDetails.getUser().getEmail();
        if (userService.deleteUser(email1)) {
            return new ResponseDto(204L, "회원탈퇴하였습니다", "");
        }
        return new ResponseDto(500L, "회원삭제 실패", "");
    }

    @ApiOperation(value = "유저 정보수정")
    @PutMapping("/users")
    @ResponseBody
    public ResponseDto editUserInfo(
            @ApiParam(value = "유저 업데이트 정보", required = true) @RequestBody EditUserRequestDto editUserInfoDto,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        EditUserResponseDto editUserResponseDto = userService.editUserInfo(editUserInfoDto, userDetails);

        return new ResponseDto(200L, "회원 정보를 수정했습니다", editUserResponseDto);

    }

    @ApiOperation(value = "카카오 로그인")
    @PostMapping("/users/kakao")
    public ResponseDto kakaoLogin(
            @ApiParam(value = "카카오 승인코드", required = true) @RequestParam String code) throws JsonProcessingException {
        if (kakaoUserService.kakaoLogin(code)) {
            return new ResponseDto(200L, "카카오 로그인 성공 !", "");
        }
        return new ResponseDto(500L, "카카오 로그인 실패 !", "");    }

    @ApiOperation(value = "마이페이지 신청내역")
    @GetMapping("/users/party")
    public ResponseDto myApplicationHistory(
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        User user = userService.userFromUserDetails(userDetails);
        List<MyApplicationPostListResponseDto> data = postService.getMyApplicationPostListByUser(user);
        return new ResponseDto(200L,"신청내역 페이지 불러오기 성공",data);
    }

    @ApiOperation(value = "마이페이지 모임관리")
    @GetMapping("/users/mypost")
    public ResponseDto myPost(
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        checkLogin(userDetails);
        User user = userService.userFromUserDetails(userDetails);
        List<MyPostPageListResponseDto> data = postService.getMyPostListByUser(user);
        return new ResponseDto(200L,"모임관리 페이지 불러오기 성공",data);
    }
    
    //내가 쓴 후기 불러오기
    @ApiOperation(value = "마이페이지 후기")
    @GetMapping("/users/myreviews")
    public ResponseDto myReviews(
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkLogin(userDetails);
        List<AllReviewResponseDto> reviewList = reviewService.getMyReviews(userDetails);
        return new ResponseDto(200L,"모임관리 페이지 불러오기 성공", reviewList);
    }

    //로그인 상태 확인
    @ApiOperation(value = "로그인 체크")
    private void checkLogin(
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new CustomErrorException("로그인이 필요합니다.");
        }
    }
}
