package com.project.joopging.security;

import javax.servlet.Filter;

public class JwtAuthenticationFilter implements Filter {
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
    }
}
