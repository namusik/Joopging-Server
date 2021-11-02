package com.project.joopging.repository;

import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.URLConnection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByDistanceAndTypeAndLocation(Distance distance, Type type, Location location);

    public List<Post> findByTypeAndLocation(Type type, Location location);

    public List<Post> findByDistanceAndLocation(Distance distance, Location location);

    public List<Post> findByDistanceAndType(Distance distance,Type type);

    public List<Post> findByDistance(Distance distance);

    public List<Post> findByLocation(Location location);

    public List<Post> findByType(Type type);
    
    //조회수 순
    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);
    
    //마감일 기준
    Page<Post> findAllByOrderByEndDateAsc(Pageable pageable);

//    @Query(value = "SELECT * FROM post ORDER BY (LIMIT_PEOPLE - NOW_PEOPLE) ASC", nativeQuery = true)
//    List<Post> findByCloseSoon();

    //작성일 기준
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    //위치 기준
    Page<Post> findAllByLocationOrderByRunningDateAsc(Pageable pageable, Location location);
    //거리 기준
    Page<Post> findAllByDistanceOrderByRunningDateAsc(Pageable pageable, Distance distance);
    //타입 기준
    Page<Post> findAllByTypeOrderByRunningDateAsc(Pageable pageable, Type type);

}
