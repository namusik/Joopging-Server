package com.project.joopging.service;

import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Join;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.JoinRepository;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final JoinRepository joinRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Join join(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 모임 입니다.")
        );

        Join join = new Join(user, post);

        post.getJoins().add(join);
        user.getJoin().add(join);
        
        //post nowpeople 1증가 시키기
        post.plusNowPeople();

        joinRepository.save(join);

        return join;
    }

    @Transactional
    public void cancleJoin(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 모임 입니다.")
        );

        Join join = joinRepository.findByUserJoinAndPostJoin(user, post).orElseThrow(
                () -> new CustomErrorException("참여신청 내역이 없습니다")
        );

        joinRepository.deleteByUserJoinAndPostJoin(user, post);

        user.getJoin().remove(join);
        post.getJoins().remove(join);

    }
}
