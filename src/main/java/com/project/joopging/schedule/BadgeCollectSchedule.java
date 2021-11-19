package com.project.joopging.schedule;

import com.project.joopging.model.Badge;
import com.project.joopging.model.Crew;
import com.project.joopging.model.User;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BadgeCollectSchedule {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void collect() {
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            List<Badge> badgeList = user.getBadges();

            //신뢰의 시작 뱃지
            if (user.getUserImg() != null) {
                Badge trustBadge = new Badge(1, 1);
                if (badgeList.contains(trustBadge)) {
                    if (user.getIntro() != null) {
                        Badge trustBadge2 = new Badge(1,2);
                        if (badgeList.contains(trustBadge2)) {

                        } else {
                            badgeList.add(trustBadge2);
                        }
                    }
                } else {
                    badgeList.add(trustBadge);
                }
            }

            // 첫 후기의 설렘 뱃지
            if (user.getReview() != null) {
                Badge firstReviewBadge = new Badge(2,1);
                if (badgeList.contains(firstReviewBadge)){
                    if (user.getReview().size() >= 10) {
                        Badge reviewKingBadge = new Badge(2,2);
                        if (badgeList.contains(reviewKingBadge)) {

                        } else {
                            badgeList.add(reviewKingBadge);
                        }
                    }
                } else {
                    badgeList.add(firstReviewBadge);
                }
            }

            // 아이줍깅 뱃지
            if (user.getBookMarks().size() >= 5) {
                Badge bookmarkBadge = new Badge(3,1);
                if (badgeList.contains(bookmarkBadge)) {
                    if (user.getBookMarks().size() >= 20) {
                        Badge bookmarkKingBadge = new Badge(3,2);
                        if (badgeList.contains(bookmarkBadge)) {

                        } else {
                            badgeList.add(bookmarkKingBadge);
                        }
                    }
                } else {
                    badgeList.add(bookmarkBadge);
                }
            }

            // 줍깅의 시작 뱃지 , 출석률 뱃지
            if (user.getCrews() != null) {
                List<Crew> crewList = user.getCrews();
                int countAttendanceTrue = 0;
                int countAttendanceFalse = 0;

                //출석률 확인
                for (Crew crew : crewList) {
                    boolean Attendance = crew.isAttendation();
                    if (Attendance) {
                        countAttendanceTrue += 1;
                    } else {
                        countAttendanceFalse += 1;
                    }
                }

                //출석률 계산
                int attendanceRate =
                        countAttendanceTrue / (countAttendanceFalse + countAttendanceFalse) * 100;

                if (countAttendanceTrue >= 1) {
                    Badge firstJoinBadge = new Badge(4,1);
                    if (badgeList.contains(firstJoinBadge)) {
                        if (countAttendanceTrue >= 10) {
                            Badge joinKingBadge = new Badge(4,2);
                            //줍깅의 시작 업그레이드
                            if (badgeList.contains(joinKingBadge)) {

                            } else {
                                badgeList.add(joinKingBadge);
                            }
                        }
                        //줍깅의 시작 뱃지
                    } else {
                        badgeList.add(firstJoinBadge);
                    }
                }

                //출석률 70프로 이하일때 나쁜출석률 뱃지
                Badge badAttendanceRateBadge = new Badge(5,1);
                if (attendanceRate <= 70) {
                    if (badgeList.contains(badAttendanceRateBadge)) {

                    } else {
                        badgeList.add(badAttendanceRateBadge);
                    }

                } else {
                    badgeList.remove(badAttendanceRateBadge);
                }

            }

            //리더쉽 뱃지
            if (user.getPost().size() >= 5) {
                Badge leadershipBadge = new Badge(6,1);
                if (badgeList.contains(leadershipBadge)) {
                    if (user.getPost().size() >= 20) {
                        Badge leaderOfLeaderBadge = new Badge(6,2);
                        if (badgeList.contains(leaderOfLeaderBadge)) {

                        } else {
                            badgeList.add(leaderOfLeaderBadge);
                        }
                    }

                } else {
                    badgeList.add(leadershipBadge);
                }
            }


        }


    }
}
