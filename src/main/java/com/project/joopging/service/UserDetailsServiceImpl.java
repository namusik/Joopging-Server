package com.project.joopging.service;

import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomErrorException("해당 email 주소의 유저를 찾을 수 없습니다."));

        return new UserDetailsImpl(user);
    }
}
