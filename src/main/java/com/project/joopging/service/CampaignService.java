package com.project.joopging.service;

import com.project.joopging.dto.campaign.CampaignCreateRequestDto;
import com.project.joopging.dto.campaign.CampaignDetailResponseDto;
import com.project.joopging.dto.campaign.CampaignUpdateRequestDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.*;
import com.project.joopging.repository.BookMarkRepository;
import com.project.joopging.repository.CampaignRepository;
import com.project.joopging.repository.CrewRepository;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final CrewRepository crewRepository;


    //켐페인 만들기
    @Transactional
    public void createCampaign(CampaignCreateRequestDto requestDto, User user) {
        Campaign campaign = Campaign.of(requestDto, user);
        Crew crew = Crew.of(user, campaign);


        // fetch Lazy 유저를 진짜 유저로 변환
        Long userId = user.getId();
        User admin = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저 정보를 찾을 수 없습니다")
        );
        //유저에도 캠페인 , 크루 추가
        List<Campaign> campaignList = admin.getCampaigns();
        campaignList.add(campaign);
        List<Crew> crewList = admin.getCrews();
        crewList.add(crew);
        List<Crew> campaignCrewList = campaign.getCrew();
        campaignCrewList.add(crew);

        campaignRepository.save(campaign);
        crewRepository.save(crew);
    }

    //캠페인 삭제
    @Transactional
    public void deleteCampaign(Long campaignId, User user) {
        Campaign campaign = getCampaignById(campaignId);
        if (campaign.isWrittenBy(user)) {
            campaignRepository.delete(campaign);
        } else {
            throw new CustomErrorException("해당 캠페인 관리자가 아닙니다 ");
        }
    }

    //캠페인 수정
    @Transactional
    public void updateCampaign(Long campaignId, CampaignUpdateRequestDto updateRequestDto, User user) {
        Campaign campaign = getCampaignById(campaignId);
        if (campaign.isWrittenBy(user)) {
            campaign.update(updateRequestDto);
        } else {
            throw new CustomErrorException("해당 캠페인 관리자가 아닙니다 ");
        }
    }

    private Campaign getCampaignById(Long postId) {
        return campaignRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("캠페인을 찾을 수 없습니다.")
        );
    }

    public Campaign getDetailCampaignId(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        campaign.setViewCount(campaign.getViewCount() + 1);
        campaignRepository.save(campaign);
        return campaign;
    }

    //캠페일 디테일 페이지
    public CampaignDetailResponseDto toSetCampaignDetailResponseDto(Campaign campaign, UserDetailsImpl userDetails) {
        boolean joinCheck;
        boolean bookmarkInfo;
        String runningDateToString = getRunningDateToString(campaign);
        if (userDetails == null) {
            return campaign.toBuildDetailCampaign(null,false,false,runningDateToString);
        } else {
            User user = userDetails.getUser();
            joinCheck = crewRepository.findByUserJoinAndCampaignJoin(user, campaign).isPresent();
            bookmarkInfo = bookMarkRepository.findByUserBookMarkAndCampaignBookMark(user, campaign).isPresent();
        }
        return campaign.toBuildDetailCampaign(userDetails, joinCheck, bookmarkInfo, runningDateToString);


    }

    private String getRunningDateToString(Campaign campaign) {
        LocalDateTime runningDate = campaign.getRunningDate();
        String day = runningDate.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(runningDate);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+day+") " + ts[1];
    }

    //북마크
    @Transactional
    public boolean getBookMarkInfo(User user, Long campaignId) {
        Long userId = user.getId();
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 유저입니다.")
        );
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(
                () -> new CustomErrorException("존재하지 않는 게시글입니다.")
        );
        Optional<BookMark> bookMark = bookMarkRepository.findByUserBookMarkAndCampaignBookMark(myUser, campaign);
        if (bookMark.isPresent()) {
            bookMarkRepository.delete(bookMark.get());
            return false;
        } else {
            BookMark myBookMark = BookMark.of(myUser, campaign);
            bookMarkRepository.save(myBookMark);
            List<BookMark> userBookMark = myUser.getBookMarks();
            userBookMark.add(myBookMark);
            List<BookMark> campaignBookMarks = campaign.getBookMarks();
            campaignBookMarks.add(myBookMark);
            return true;
        }
    }
}
