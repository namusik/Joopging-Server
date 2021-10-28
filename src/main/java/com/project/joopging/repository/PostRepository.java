package com.project.joopging.repository;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByDistanceAndTypeAndLocation(Integer distance, Integer type, Integer location);

    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Post> findAllByOrderByRunningDateAsc(Pageable pageable);

    @Query(value = "SELECT * FROM POST ORDER BY (LIMIT_PEOPLE - NOW_PEOPLE) ASC", nativeQuery = true)
    Page<Post> findByCloseSoon(Pageable pageable);

    Page<Post> findAllByLocationOrderByRunningDateAsc(Pageable pageable, Location location);

    Page<Post> findAllByDistanceOrderByRunningDateAsc(Pageable pageable, Distance distance);

    Page<Post> findAllByTypeOrderByRunningDateAsc(Pageable pageable, Type type);
}
