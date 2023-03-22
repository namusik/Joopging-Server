package com.project.joopging.repository;

import com.project.joopging.model.Post;
import com.project.joopging.model.Review;
import com.project.joopging.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.HttpSessionListener;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByOrderByModifiedAtDesc(Pageable pageable);

    boolean existsByPostReviewAndUserReview(Post post, User user);


}
