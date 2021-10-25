package com.project.joopging.service;

import com.project.joopging.dto.post.PostCreateRequestDto;
import com.project.joopging.dto.user.SignupRequestDto;

import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public User userFromUserDetails(UserDetails userDetails) {
        if ( userDetails instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) userDetails).getUser();
        } else {
            throw new CustomErrorException("로그인이 필요합니다.");
        }
    }

    public boolean registerUser(SignupRequestDto requestDto) {
//        //User user = signupValidator.validate(requestDto);
//        userRepository.save(user);
        return true;
    }

}
