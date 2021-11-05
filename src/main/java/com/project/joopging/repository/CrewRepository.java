package com.project.joopging.repository;

import com.project.joopging.model.Crew;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    void deleteByUserJoinAndPostJoin(User user, Post post);

    Optional<Crew> findByUserJoinAndPostJoin(User user, Post post);

    List<Crew> findAllByUserJoin(User user);
}
