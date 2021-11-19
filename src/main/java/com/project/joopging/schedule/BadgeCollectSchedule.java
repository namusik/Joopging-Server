package com.project.joopging.schedule;

import com.project.joopging.model.Badge;
import com.project.joopging.model.Crew;
import com.project.joopging.model.User;
import com.project.joopging.repository.BadgeRepository;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BadgeCollectSchedule {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void collect() {
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            List<Badge> badgeList = user.getBadges();

            //신뢰의 시작 뱃지
            if (user.getUserImg() != null) {
                Badge trustBadge = Badge.of(1, 1, user);
                if (badgeList.contains(trustBadge)) {
                    if (user.getIntro() != null) {
                        Badge trustBadge2 = Badge.of(1,2, user);
                        if (badgeList.contains(trustBadge2)) {

                        } else {
                            badgeList.add(trustBadge2);
                            badgeRepository.save(trustBadge2);
                        }
                    }
                } else {
                    badgeList.add(trustBadge);
                    badgeRepository.save(trustBadge);
                }
            }

            // 첫 후기의 설렘 뱃지
            if (user.getReview() != null) {
                Badge firstReviewBadge = Badge.of(2,1, user);
                if (badgeList.contains(firstReviewBadge)){
                    if (user.getReview().size() >= 10) {
                        Badge reviewKingBadge = Badge.of(2,2, user);
                        if (badgeList.contains(reviewKingBadge)) {

                        } else {
                            badgeList.add(reviewKingBadge);
                            badgeRepository.save(reviewKingBadge);
                        }
                    }
                } else {
                    badgeList.add(firstReviewBadge);
                    badgeRepository.save(firstReviewBadge);
                }
            }

            // 아이줍깅 뱃지
            if (user.getBookMarks().size() >= 5) {
                Badge bookmarkBadge = Badge.of(3,1, user);
                if (badgeList.contains(bookmarkBadge)) {
                    if (user.getBookMarks().size() >= 20) {
                        Badge bookmarkKingBadge = Badge.of(3,2, user);
                        if (badgeList.contains(bookmarkBadge)) {

                        } else {
                            badgeList.add(bookmarkKingBadge);
                            badgeRepository.save(bookmarkKingBadge);
                        }
                    }
                } else {
                    badgeList.add(bookmarkBadge);
                    badgeRepository.save(bookmarkBadge);
                }
            }

            //리더쉽 뱃지
            if (user.getPost().size() >= 5) {
                Badge leadershipBadge = Badge.of(6,1, user);
                if (badgeList.contains(leadershipBadge)) {
                    if (user.getPost().size() >= 20) {
                        Badge leaderOfLeaderBadge = Badge.of(6,2, user);
                        if (badgeList.contains(leaderOfLeaderBadge)) {

                        } else {
                            badgeList.add(leaderOfLeaderBadge);
                            badgeRepository.save(leaderOfLeaderBadge);
                        }
                    }

                } else {
                    badgeList.add(leadershipBadge);
                    badgeRepository.save(leadershipBadge);
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


                if (countAttendanceTrue >= 1) {
                    Badge firstJoinBadge = Badge.of(4,1, user);
                    if (badgeList.contains(firstJoinBadge)) {
                        if (countAttendanceTrue >= 10) {
                            Badge joinKingBadge = Badge.of(4,2, user);
                            //줍깅의 시작 업그레이드
                            if (badgeList.contains(joinKingBadge)) {

                            } else {
                                badgeList.add(joinKingBadge);
                                badgeRepository.save(joinKingBadge);
                            }
                        }
                        //줍깅의 시작 뱃지
                    } else {
                        badgeList.add(firstJoinBadge);
                        badgeRepository.save(firstJoinBadge);
                    }
                }


                //출석률 계산
                if (countAttendanceTrue >= 1 & countAttendanceFalse >= 1) {
                    int attendanceRate =
                            countAttendanceTrue / (countAttendanceFalse + countAttendanceFalse) * 100;
                    //출석률 70프로 이하일때 나쁜출석률 뱃지
                    Badge badAttendanceRateBadge = Badge.of(5, 1, user);
                    if (attendanceRate <= 70) {
                        if (badgeList.contains(badAttendanceRateBadge)) {

                        } else {
                            badgeList.add(badAttendanceRateBadge);
                            badgeRepository.save(badAttendanceRateBadge);
                        }

                    } else {
                        badgeList.remove(badAttendanceRateBadge);
                        badgeRepository.delete(badAttendanceRateBadge);
                    }

                } else {
                    System.out.println("참가 이력이 없습니다");
                }

            }




        }


    }
}
