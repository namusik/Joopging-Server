package com.project.joopging.util;

import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws CustomErrorException {
        final String token = request.getHeader("X-AUTH-TOKEN");
        log.info("preHandle: " + token);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomErrorException("권한이 없습니다");
        }
        request.setAttribute("userID", jwtTokenProvider.getAuthentication(token));
        return true;
    }
}
