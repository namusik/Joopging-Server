package com.project.joopging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.user.*;
import com.project.joopging.model.User;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.KakaoUserService;
import com.project.joopging.service.PostService;
import com.project.joopging.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Post Controller Api V1")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoUserService kakaoUserService;
    private final PostService postService;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, KakaoUserService kakaoUserService, PostService postService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoUserService = kakaoUserService;
        this.postService = postService;
    }

    @PostMapping("/users")
    @ResponseBody
    public ResponseDto createUser(@RequestBody SignupRequestDto signupRequestDto) {
        if (userService.registerUser(signupRequestDto)) {
            String username = signupRequestDto.getNickname();
            log.info(username + "님 환영합니다 !");
            return new ResponseDto(200L, "회원가입에 성공하였습니다 !", "");
        }
        return new ResponseDto(500L, "회원가입에 실패하였습니다 ...", "");
    }

    @PostMapping("/users/login")
    @ResponseBody
    public ResponseDto login(@RequestBody LoginUserDto loginUserDto, HttpServletResponse response) {
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

    @DeleteMapping("/users")
    @ResponseBody
    public ResponseDto deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email1 = userDetails.getUser().getEmail();
        if (userService.deleteUser(email1)) {
            return new ResponseDto(200L, "회원을 삭제했습니다", "");
        }
        return new ResponseDto(500L, "회원삭제 실패", "");
    }


    @PutMapping("/users")
    @ResponseBody
    public ResponseDto editUserInfo(@RequestBody EditUserRequestDto editUserInfoDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        EditUserResponseDto editUserResponseDto = userService.editUserInfo(editUserInfoDto, userDetails);

        return new ResponseDto(200L, "회원 정보를 수정했습니다", editUserResponseDto);

    }

    @PostMapping("/kakao")
    public ResponseDto kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        if (kakaoUserService.kakaoLogin(code)) {
            return new ResponseDto(200L, "카카오 로그인 성공 !", "");
        }
        return new ResponseDto(500L, "카카오 로그인 실패 !", "");    }

    @ApiOperation(value = "마이페이지 신청내역")
    @GetMapping("/users/party")
    public ResponseDto myApplicationHistory(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.userFromUserDetails(userDetails);
        List<MyApplicationPostListResponseDto> data = postService.getMyApplicationPostListByUser(user);
        return new ResponseDto(200L,"신청내역 페이지 불러오기 성공",data);
    }

    @ApiOperation(value = "마이페이지 모임관리")
    @GetMapping("/users/mypost")
    public ResponseDto myPost(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.userFromUserDetails(userDetails);
        List<MyPostPageListResponseDto> data = postService.getMyPostListByUser(user);
        return new ResponseDto(200L,"모임관리 페이지 불러오기 성공",data);
    }
}
