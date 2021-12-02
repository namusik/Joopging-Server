//package com.project.joopging.service;
//
//import com.project.joopging.dto.user.SignupRequestDto;
//import com.project.joopging.model.User;
//import com.project.joopging.repository.UserRepository;
//import com.project.joopging.security.JwtTokenProvider;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private JwtTokenProvider jwtTokenProvider;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    void userFromUserDetails() {
//    }
//
//    @Test
//    @DisplayName("회원가입")
//    public void registerUser() throws Exception {
//        String email = "test@test.test";
//        String nickname = "test";
//        String password = "password";
//        String userImg = "test_img";
//        String location = "test_loc";
//        String type = "test_type";
//        String distance = "test_dis";
//        String number = "test_num";
//
//        SignupRequestDto signupRequestDto = new SignupRequestDto(
//                email, nickname, password, userImg, location, type, distance, number
//        );
//
//        //when
//        User user = userService.registerUser(signupRequestDto);
//
//        //then
//        assertEquals(signupRequestDto.getNickname(), user.getNickname());
//        assertEquals(signupRequestDto.getDistance(), user.getDistance());
//        assertEquals(signupRequestDto.getEmail(), user.getEmail());
//        assertEquals(signupRequestDto.getImage(), user.getUserImg());
//        assertEquals(signupRequestDto.getLocation(), user.getLocation());
//        assertEquals(signupRequestDto.getType(), user.getType());
//        assertEquals(signupRequestDto.getNumber(), user.getNumber());
//        assertEquals(signupRequestDto.getPassword(), user.getPassword());
//    }
//
//    @Test
//    void login() {
//    }
//
//
//
//    @Test
//    void editUserInfo() {
//    }
//}