package com.project.joopging.service;

import com.project.joopging.dto.user.SignupRequestDto;

import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public boolean registerUser(SignupRequestDto requestDto) {
//        //User user = signupValidator.validate(requestDto);
//        userRepository.save(user);
        return true;
    }

}
