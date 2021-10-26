package com.project.joopging.repository;

import com.project.joopging.model.Party;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    void deleteByUserJoinAndPostJoin(User user, Post post);

    Optional<Party> findByUserJoinAndPostJoin(User user, Post post);
}
