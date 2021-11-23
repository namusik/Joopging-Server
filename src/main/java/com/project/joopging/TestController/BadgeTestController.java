package com.project.joopging.TestController;

import com.project.joopging.dto.ResponseDto;
import com.project.joopging.model.Badge;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import com.project.joopging.schedule.BadgeCollectSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BadgeTestController {

    private final BadgeCollectSchedule badgeCollectSchedule;
    private final UserRepository userRepository;

    @GetMapping("/test")
    public ResponseDto badgeTest() {
        badgeCollectSchedule.collect();
        return new ResponseDto(200L,"뱃지 스케쥴러가 실행되었습니다", "");
    }

    @GetMapping("/test2")
    public ResponseDto containTest() {
        User user = userRepository.findById(2L).orElseThrow(
                () -> new NullPointerException("유저가 없습니다")
        );
        List<Badge> badgeList = user.getBadges();
        List<Integer> categoryList = new ArrayList<>();
        for (Badge badge : badgeList) {
            categoryList.add(badge.getCategory());
        }
        Badge badge = Badge.of(6,1,user);
        System.out.println(categoryList.contains(badge.getCategory()));

        return new ResponseDto(200L, "테스트실행", "");
    }
}
