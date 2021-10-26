package com.project.joopging.service;

import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Party;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.PartyRepository;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Party join(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );
        System.out.println("user = " + user);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 모임 입니다.")
        );
        System.out.println("post = " + post);
        Party party = new Party(user, post);

        post.getJoins().add(party);
        //post nowpeople 1증가 시키기
        post.plusNowPeople();

        user.getJoin().add(party);

        partyRepository.save(party);

        return party;
    }

    @Transactional
    public void cancleJoin(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 모임 입니다.")
        );

        Party party = partyRepository.findByUserJoinAndPostJoin(user, post).orElseThrow(
                () -> new CustomErrorException("참여신청 내역이 없습니다")
        );

        partyRepository.deleteByUserJoinAndPostJoin(user, post);

        user.getJoin().remove(party);
        post.getJoins().remove(party);

        //post nowPeople 1 감소 시키기
        post.minusNowPeople();

    }
}
