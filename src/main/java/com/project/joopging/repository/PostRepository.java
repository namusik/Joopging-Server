package com.project.joopging.repository;

import com.project.joopging.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Post findByDistanceAndTypeAndLocation(Integer distance, Integer type, Integer location);

    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Post> findAllByLocationOrderByRunningDateDesc(Pageable pageable, String location);
}
