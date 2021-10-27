package com.project.joopging.repository;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Post findByDistanceAndTypeAndLocation(Distance distance, Type type, Location location);

}
