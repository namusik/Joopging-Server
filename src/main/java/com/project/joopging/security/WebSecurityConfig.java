package com.project.joopging.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    //h2-console 사용에 대한 허용 (CSRF, FrameOption 무시)
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**","/v2/api-docs",
                "/v3/api-docs",
                "/swagger-resources/**", "/swagger-ui.html", "/swagger-ui/index.html#/",
                "/webjars/**", "/swagger/**", "/configuration/**");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.headers().frameOptions().disable()
                .and()
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                .csrf().disable() // csrf 보안 토큰 disable처리, rest api이므로 csrf 보안이 필요없으므로 disable처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/users/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/main").permitAll()
                .antMatchers(HttpMethod.GET, "/reviews").permitAll()
                .antMatchers(HttpMethod.GET, "/reviews/{review_id}").permitAll()
                .antMatchers("/posts/{post_id}").permitAll()
                .antMatchers("/posts").permitAll()
                .antMatchers("/exception/**").permitAll()
                .antMatchers("/enum/**").permitAll()
                //swagger
                .antMatchers("/swagger-ui/index.html#").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/configuration/**").permitAll()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/swagger-ui/index.html").permitAll()
                //nginx
                .antMatchers("/profile").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/health").permitAll()
                .antMatchers("/version").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
    }

}