package com.project.joopging.controller;

import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.exception.NoTokenException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExceptionController {
    @GetMapping("/exception/entrypoint")
    public void entryPoint() {
        throw new NoTokenException("로그인이 필요합니다.");
    }
}
