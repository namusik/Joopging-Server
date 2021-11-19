package com.project.joopging.TestController;

import com.project.joopging.schedule.BadgeCollectSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BadgeTestController {

    private final BadgeCollectSchedule badgeCollectSchedule;

    @GetMapping("/test")
    public ResponseDto badgeTest() {
        badgeCollectSchedule.collect();
        return new ResponseDto(200L,"뱃지 스케쥴러가 실행되었습니다", "");
    }
}
