package com.project.joopging.schedule;

import com.project.joopging.model.Badge;
import com.project.joopging.model.Crew;
import com.project.joopging.model.User;
import com.project.joopging.repository.BadgeRepository;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
            List<Integer> categoryList = new ArrayList<>();
            List<Integer> levelList = new ArrayList<>();


            for (Badge badge : badgeList) {
                categoryList.add(badge.getCategory());
                levelList.add(badge.getLevel());
            }

            //신뢰의 시작 뱃지
            if (user.getUserImg() != null) {
                Badge trustBadge = Badge.of(1, 1, user);
                if (categoryList.contains(trustBadge.getCategory())) {
                    if (user.getIntro() != null) {
                        Badge trustBadge2 = Badge.of(1,11, user);
                        if (levelList.contains(trustBadge2.getLevel())) {

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
            if (user.getReview().size() >= 1) {
                Badge firstReviewBadge = Badge.of(2,2, user);
                if (categoryList.contains(firstReviewBadge.getCategory())){
                    if (user.getReview().size() >= 10) {
                        Badge reviewKingBadge = Badge.of(2,22, user);
                        if (levelList.contains(reviewKingBadge.getLevel())) {

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
                Badge bookmarkBadge = Badge.of(3,3, user);
                if (categoryList.contains(bookmarkBadge.getCategory())) {
                    if (user.getBookMarks().size() >= 20) {
                        Badge bookmarkKingBadge = Badge.of(3,33, user);
                        if (levelList.contains(bookmarkKingBadge.getLevel())) {

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
                Badge leadershipBadge = Badge.of(6,6, user);
                if (categoryList.contains(leadershipBadge.getCategory())) {
                    if (user.getPost().size() >= 20) {
                        Badge leaderOfLeaderBadge = Badge.of(6,66, user);
                        if (levelList.contains(leaderOfLeaderBadge.getLevel())) {

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
            if (user.getCrews().size() >= 1) {
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
                    Badge firstJoinBadge = Badge.of(4,4, user);
                    if (categoryList.contains(firstJoinBadge.getCategory())) {
                        if (countAttendanceTrue >= 10) {
                            Badge joinKingBadge = Badge.of(4,44, user);
                            //줍깅의 시작 업그레이드
                            if (levelList.contains(joinKingBadge.getLevel())) {

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
                if (countAttendanceTrue >= 1 ) {
                    int attendanceRate =
                            countAttendanceTrue / (countAttendanceTrue + countAttendanceFalse) * 100;
                    System.out.println(attendanceRate);
                    //출석률 70프로 이하일때 나쁜출석률 뱃지
                    Badge badAttendanceRateBadge = Badge.of(5, 5, user);
                    if (attendanceRate <= 70) {
                        if (categoryList.contains(badAttendanceRateBadge.getCategory())) {

                        } else {
                            badgeList.add(badAttendanceRateBadge);
                            badgeRepository.save(badAttendanceRateBadge);
                        }

                    } else {
                        Badge DubBadge = badgeRepository.findByUserBadgeAndCategoryAndLevel(user,5,5);
                        badgeList.remove(DubBadge);
                        badgeRepository.delete(DubBadge);
                    }

                } else {
//                    System.out.println("참가 이력이 없습니다");
                }

            }




        }


    }
}
