package com.project.joopging.repository;

import com.project.joopging.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Post findByDistanceAndTypeAndLocation(Integer distance, Integer type, Integer location);

}
