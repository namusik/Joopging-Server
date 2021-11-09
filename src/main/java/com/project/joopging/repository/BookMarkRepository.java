package com.project.joopging.repository;

import com.project.joopging.model.BookMark;
import com.project.joopging.model.Campaign;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByUserBookMarkAndPostBookMark(User userId, Post postId);

    Optional<BookMark> findByUserBookMarkAndCampaignBookMark(User adminId, Campaign campaignId);
}
