package com.project.joopging.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final Environment env;

    //현재 어떤 포트로 돌아가는지 확인용
    @GetMapping("/profile")
    public String profile() {
        //현재 동작중인 프로파일의 이름을 반환
        return Arrays.stream(env.getActiveProfiles()).findFirst().orElse("");
    }

    //version 확인용
    @GetMapping("/version")
    public String checkVersion() {
        return "ver4";
    }

    @GetMapping("/hello")
    public String hello() {
        return "jenkins build Success332333";
    }
}
