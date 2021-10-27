package com.project.joopging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.user.*;
import com.project.joopging.model.User;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.service.KakaoUserService;
import com.project.joopging.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoUserService kakaoUserService;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoUserService = kakaoUserService;
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
        return new ResponseDto(500L, "카카오 로그인 실패 !", "");
    }
}
