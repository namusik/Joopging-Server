package com.project.joopging.service;

import com.project.joopging.dto.crew.CrewReponseDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Campaign;
import com.project.joopging.model.Crew;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;

import com.project.joopging.repository.CampaignRepository;
import com.project.joopging.repository.CrewRepository;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewService {

    private final CrewRepository crewRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CampaignRepository campaignRepository;

    @Transactional
    public Crew join(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 모임 입니다.")
        );

        //혹시 이미 참여한 사람이 또 참여신청을 눌렀는지 이중확인
        crewRepository.findByUserJoinAndPostJoin(user,post).ifPresent(
                m -> {
                    throw new CustomErrorException("이미 참여되어있습니다");}
        );
        Crew crew = new Crew(user, post);
        post.getCrew().add(crew);
        //post nowpeople 1증가 시키기
        post.plusNowPeople();

        user.getCrews().add(crew);

        crewRepository.save(crew);

        return crew;
    }

    @Transactional
    public void cancelJoin(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 모임 입니다.")
        );
        //이중 체크
        Crew crew = crewRepository.findByUserJoinAndPostJoin(user, post).orElseThrow(
                () -> new CustomErrorException("참여신청 내역이 없습니다")
        );

        crewRepository.deleteByUserJoinAndPostJoin(user, post);

        //post nowPeople 1 감소 시키기
        post.minusNowPeople();

    }

    @Transactional
    public Crew joinCampaign(Long campaign_id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );
        Campaign campaign = campaignRepository.findById(campaign_id).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 캠페인 입니다")
        );
        //혹시 이미 참여한 사람이 또 참여신청을 눌렀는지 이중확인
        crewRepository.findByUserJoinAndCampaignJoin(user,campaign).ifPresent(
                m -> {
                    throw new CustomErrorException("이미 참여되어있습니다");}
        );

        Crew crew = Crew.of(user, campaign);
        campaign.getCrew().add(crew);
        campaign.plusNowPeople();
        user.getCrews().add(crew);

        crewRepository.save(crew);
        return crew;
    }

    @Transactional
    public void cancelJoinCampaign(Long campaign_id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 사용자 입니다.")
        );

        Campaign campaign = campaignRepository.findById(campaign_id).orElseThrow(
                () -> new CustomErrorException("찾을 수 없는 모임 입니다.")
        );
        //이중 체크
        Crew crew = crewRepository.findByUserJoinAndCampaignJoin(user, campaign).orElseThrow(
                () -> new CustomErrorException("참여신청 내역이 없습니다")
        );

        crewRepository.deleteByUserJoinAndCampaignJoin(user, campaign);

        //campaign nowPeople 1 감소 시키기
        campaign.minusNowPeople();
    }

//    public List<CrewReponseDto> getCrews(Long postId) {
//
//    }
}
