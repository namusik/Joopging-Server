package com.project.joopging.repository;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByDistanceAndTypeAndLocation(Distance distance, Type type, Location location);

    public List<Post> findByTypeAndLocation(Type type, Location location);

    public List<Post> findByDistanceAndLocation(Distance distance, Location location);

    public List<Post> findByDistanceAndType(Distance distance,Type type);

    public List<Post> findByDistance(Distance distance);

    public List<Post> findByLocation(Location location);

    public List<Post> findByType(Type type);

    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Post> findAllByLocationOrderByRunningDateDesc(Pageable pageable, String location);



}
