package com.project.joopging.dto.user;

import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class signupValidator {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User validate(SignupRequestDto requestDto) {
        return null;}
}
