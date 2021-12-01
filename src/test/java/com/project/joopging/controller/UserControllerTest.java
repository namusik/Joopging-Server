package com.project.joopging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.joopging.MockSpringSecurityFilter;
import com.project.joopging.dto.user.*;
import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.JwtTokenProvider;
import com.project.joopging.security.UserDetailsImpl;
import com.project.joopging.security.WebSecurityConfig;
import com.project.joopging.service.KakaoUserService;
import com.project.joopging.service.PostService;
import com.project.joopging.service.ReviewService;
import com.project.joopging.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@WebMvcTest(
        controllers = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;
    @MockBean
    private PostService postService;
    @MockBean
    private ReviewService reviewService;
    @MockBean
    private KakaoUserService kakaoUserService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    private Principal mockPrincipal;
    @Autowired
    private ObjectMapper objectMapper;

    User testUser;
    UserDetailsImpl mockUserDetails;
    SecurityContext securityContext;


    @BeforeEach
    private void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();

        testUser = new User(
                "테스트코드",
                "ksk123",
                "ksk@ksk.ksk",
                UserRoleEnum.USER,
                "강동구",
                "산에서",
                "7km",
                "null",
                "010-0000-0000"
        );
        mockUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(mockUserDetails, "", Collections.emptyList());

    }

    private void authenticated() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUserDetails, "", mockUserDetails.getAuthorities());
        securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    @Test
    @DisplayName("유저 정보 조회 및 전송")
    void getUserInfo() throws Exception {
        mvc.perform(get("/users")
                        .principal(mockPrincipal))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        verify(userService, atLeastOnce()).detailsUserInfo(mockUserDetails);
    }

    @Test
    @DisplayName("유저 정보 수정")
    public void updateUserInfo() throws Exception {
        Long id = testUser.getId();
        String userImg = "test_img";
        String location = "test_loc";
        String type = "test_type";
        String distance = "test_dis";
        String intro = "test_intro";
        UserRoleEnum roleEnum = UserRoleEnum.USER;

        EditUserResponseDto editUserResponseDto = new EditUserResponseDto(id, userImg, location, type, distance, roleEnum, intro);

        when(userService.editUserInfo(any(), any())).thenReturn(editUserResponseDto);

        mvc.perform(put("/users")
                        .principal(mockPrincipal)
                        .content(objectMapper.writeValueAsString(editUserResponseDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.userImg").value(editUserResponseDto.getUserImg()))
                .andExpect(jsonPath("$.data.location").value(editUserResponseDto.getLocation()))
                .andExpect(jsonPath("$.data.type").value(editUserResponseDto.getType()))
                .andExpect(jsonPath("$.data.distance").value(editUserResponseDto.getDistance()))
                .andExpect(jsonPath("$.data.intro").value(editUserResponseDto.getIntro()));
    }

//    @Test
//    @DisplayName("로그인 요청")
//    public void login() throws Exception {
//        String email = "ksk@ksk.ksk";
//        String password = "ksk123";
//
//        LoginUserDto loginUserDto = new LoginUserDto(email, password);
//
//        mvc.perform(post("/users/login")
//                        .content(objectMapper.writeValueAsString(loginUserDto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .principal(mockPrincipal)
//                        .characterEncoding("utf-8"))
//                .andExpect(status().is2xxSuccessful())
//                .andDo(MockMvcResultHandlers.print());
//
//        verify(userService, atLeastOnce()).login(refEq(loginUserDto));
//    }

    @Test
    @DisplayName("회원 가입 요청")
    public void createUser() throws Exception {
        String email = "test@test.test";
        String password = "test_password";
        String nickname = "test";
        String userImg = "test_img";
        String location = "test_loc";
        String type = "test_type";
        String distance = "test_dis";
        String intro = "test_intro";

        SignupRequestDto signupRequestDto = new SignupRequestDto(
                email, password, nickname, userImg, location, type, distance, intro
        );

        mvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(signupRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                        .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());

        verify(userService, atLeastOnce()).registerUser(refEq(signupRequestDto));
    }
}