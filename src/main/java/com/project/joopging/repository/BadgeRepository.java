package com.project.joopging.repository;

import com.project.joopging.model.Badge;
import com.project.joopging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByUserBadgeAndCategoryAndLevel(User user, int category, int level);
}
