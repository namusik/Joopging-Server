package com.project.joopging.repository;

import com.project.joopging.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByDistanceAndTypeAndLocation(String distance, String type, String location);

    public List<Post> findByTypeAndLocation(String type, String location);

    public List<Post> findByDistanceAndLocation(String distance, String location);

    public List<Post> findByDistanceAndType(String distance,String type);

    public List<Post> findByDistance(String distance);

    public List<Post> findByLocation(String location);

    public List<Post> findByType(String type);
    
    //조회수 순
    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);
    
    //마감일 기준
    Page<Post> findAllByOrderByEndDateAsc(Pageable pageable);

//    @Query(value = "SELECT * FROM post ORDER BY (LIMIT_PEOPLE - NOW_PEOPLE) ASC", nativeQuery = true)
//    List<Post> findByCloseSoon();

    //작성일 기준
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    //위치 기준
    Page<Post> findAllByLocationOrderByRunningDateAsc(Pageable pageable, String location);
    //거리 기준
    Page<Post> findAllByDistanceOrderByRunningDateAsc(Pageable pageable, String distance);
    //타입 기준
    Page<Post> findAllByTypeOrderByRunningDateAsc(Pageable pageable, String type);

}
