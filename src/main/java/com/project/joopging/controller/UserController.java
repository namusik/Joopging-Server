package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.user.DeleteUserDto;
import com.project.joopging.dto.user.LoginResponseDto;
import com.project.joopging.dto.user.LoginUserDto;
import com.project.joopging.dto.user.SignupRequestDto;
import com.project.joopging.model.User;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
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
        loginResponseDto.setUser(user);
        loginResponseDto.setJwtToken(token);

        return new ResponseDto(200L, "로그인에 성공했습니다", loginResponseDto);
    }

    @DeleteMapping("/users")
    @ResponseBody
    public ResponseDto deleteUser(@RequestBody DeleteUserDto deleteUserDto) {
        String email = deleteUserDto.getEmail();
        if(userService.deleteUser(email)) {
            return new ResponseDto(200L, "회원을 삭제했습니다", "");
        }
        return new ResponseDto(500L, "회원삭제 실패", "");
    }


}
