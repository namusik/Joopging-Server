package com.project.joopging.service;

import com.project.joopging.dto.badge.MyBadgeListResponseDto;
import com.project.joopging.model.Badge;
import com.project.joopging.model.User;
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

    @Transactional(readOnly = true)
    public List<MyBadgeListResponseDto> getMyBadgeListByUser(User user) {

        List<MyBadgeListResponseDto> responseDtoList = new ArrayList<>();
        List<Badge> badgeList = user.getBadges();
        for (Badge badge : badgeList) {
            String modifiedAtToString = getModifiedAtToString(badge);
            MyBadgeListResponseDto responseDto = badge.toBuildBadge(modifiedAtToString);
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }





    //
    private String getModifiedAtToString(Badge badge) {
        LocalDateTime modifiedAt = badge.getModifiedAt();
        String day = modifiedAt.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = String.valueOf(modifiedAt);
//        System.out.println("date = " + date);
        String[] ts = date.split("T");
        return ts[0] + " ("+day+") " + ts[1];
    }
}
