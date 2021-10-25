package com.project.joopging.repository;

import com.project.joopging.model.Join;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoinRepository extends JpaRepository<Join, Long> {

    void deleteByUserJoinAndPostJoin(User user, Post post);

    Optional<Join> findByUserJoinAndPostJoin(User user, Post post);
}
