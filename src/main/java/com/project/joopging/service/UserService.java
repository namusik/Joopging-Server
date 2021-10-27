package com.project.joopging.service;

import com.project.joopging.dto.user.*;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final signupValidator signupValidator;
    private final PasswordEncoder passwordEncoder;

    public User userFromUserDetails(UserDetails userDetails) {
        if ( userDetails instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) userDetails).getUser();
        } else {
            throw new CustomErrorException("로그인이 필요합니다.");
        }
    }

    public boolean registerUser(SignupRequestDto requestDto) {
        User user = signupValidator.validate(requestDto);
        userRepository.save(user);
        return true;
    }

    public User login(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(
                () -> new CustomErrorException("이메일을 찾을 수 없습니다")
        );
        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new CustomErrorException("비밀번호가 일치하지 않습니다");
        }
        return user;
    }

    public boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomErrorException("이메일을 찾을 수 없습니다")
        );
        userRepository.delete(user);
        return true;
    }

    public EditUserResponseDto editUserInfo(EditUserRequestDto editUserInfoDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String userImg = editUserInfoDto.getUserImg();
        String intro = editUserInfoDto.getIntro();

        String password;
        if (user.getPassword().equals(editUserInfoDto.getPassword())) {
            password = user.getPassword();
        } else {
            password = editUserInfoDto.getPassword();
        }

        Integer distance = editUserInfoDto.getDistance();
        Distance distanceName = Distance.getDistanceById(distance);

        Integer location = editUserInfoDto.getLocation();
        Location locationName = Location.getLocationById(location);

        Integer type = editUserInfoDto.getType();
        Type typeName = Type.getTypeById(type);

        user.setUserImg(userImg);
        user.setDistance(distanceName);
        user.setPassword(passwordEncoder.encode(password));
        user.setLocation(locationName);
        user.setType(typeName);
        user.setIntro(intro);

        userRepository.save(user);

        return new EditUserResponseDto(user);
    }

    public LoginDetailReponseDto toSetLoginDetailResponse(User user) {
        return user.toBuildDetailUser();
    }
}
