package com.project.joopging.controller;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.dto.user.SignupRequestDto;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
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
            return new ResponseDto(200L, "회원가입에 성공하였습니다 !", null);
        }
        return new ResponseDto(500L, "회원가입에 실패하였습니다 ...", null);
    }

}
