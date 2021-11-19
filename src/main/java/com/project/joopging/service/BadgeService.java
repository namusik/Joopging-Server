package com.project.joopging.service;

import com.project.joopging.dto.badge.MyBadgeListResponseDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Badge;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<MyBadgeListResponseDto> getMyBadgeListByUser(User user) {

        Long userId = user.getId();
        //프록시에서 진짜 유저로 변환
        User myUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저가 존재하지 않습니다")
        );
        List<MyBadgeListResponseDto> responseDtoList = new ArrayList<>();
        List<Badge> badgeList = myUser.getBadges();
        for (Badge badge : badgeList) {
            String createdAtToString = getCreatedAtToString(badge);
            MyBadgeListResponseDto responseDto = badge.toBuildBadge(createdAtToString);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }





    // 뱃지 생성일 보내주기
    private String getCreatedAtToString(Badge badge) {
        LocalDateTime modifiedAt = badge.getCreatedAt();
        String day = modifiedAt.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(modifiedAt);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+day+") " + ts[1];
    }
}
