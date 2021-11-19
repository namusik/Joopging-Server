package com.project.joopging.TestController;

import com.project.joopging.schedule.BadgeCollectSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BadgeTestController {

    private final BadgeCollectSchedule badgeCollectSchedule;


}
