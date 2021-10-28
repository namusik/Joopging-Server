package com.project.joopging.repository;

import com.project.joopging.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
