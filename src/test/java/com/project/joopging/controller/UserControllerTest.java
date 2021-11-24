//package com.project.joopging.controller;
//
//import com.project.joopging.dto.user.SignupRequestDto;
//import com.project.joopging.enums.UserRoleEnum;
//import com.project.joopging.model.User;
//import com.project.joopging.repository.UserRepository;
//import com.project.joopging.security.JwtTokenProvider;
//import com.project.joopging.security.UserDetailsImpl;
//import com.project.joopging.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebAppConfiguration
//@AutoConfigureMockMvc(addFilters = false)
//@WebMvcTest(controllers = UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private JwtTokenProvider jwtTokenProvider;
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    User testUser;
//    UserDetailsImpl mockUserDetails;
//    SecurityContext securityContext;
//
//
//    @BeforeEach
//    private void setUp() {
//        testUser = new User(
//                "ksk123",
//                "ksk123",
//                "ksk@ksk.ksk",
//                UserRoleEnum.USER,
//                "강동구",
//                "산에서",
//                "7km",
//                "010-0000-0000"
//        );
//        mockUserDetails = new UserDetailsImpl(testUser);
//    }
//    private void authenticated() {
//        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUserDetails, "", mockUserDetails.getAuthorities());
//        securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authentication);
//    }
//
//    @Test
//    void getUserInfo() throws Exception {
//        //given
//        authenticated();
//        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
//                .email(testUser.getEmail())
//                .password(testUser.getPassword())
//                .nickname(testUser.getNickname())
//                .distance(testUser.getDistance())
//                .location(testUser.getLocation())
//                .type(testUser.getType())
//                .build();
//
//        given(userService.registerUser(signupRequestDto))
//                .willReturn(true);
//        //when
//        mvc.perform(get("/users"))
//                .andDo(print())
//                //then
//                .andExpect(jsonPath("$.user.email").value(testUser.getEmail()))
//                .andExpect(jsonPath("$.responseMessage").value("회원가입에 성공하였습니다 !"))
//                .andExpect(jsonPath("$.statusCode").value("200"));
//    }
//
//
//}