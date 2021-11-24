package com.project.joopging.repository;

import com.project.joopging.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    Page<Post> findAllByEndDateGreaterThanOrderByViewCountDesc(Pageable pageable, LocalDate today);
    
    //마감일 기준
    Page<Post> findAllByEndDateGreaterThanOrderByEndDateAsc(Pageable pageable, LocalDate today);

//    @Query(value = "SELECT * FROM post ORDER BY (LIMIT_PEOPLE - NOW_PEOPLE) ASC", nativeQuery = true)
//    List<Post> findByCloseSoon();

    //작성일 기준
    Page<Post> findAllByEndDateGreaterThanOrderByCreatedAtDesc(Pageable pageable, LocalDate today);

    //위치 기준
    Page<Post> findAllByLocationAndEndDateGreaterThanOrderByRunningDateAsc(Pageable pageable, String location, LocalDate today);
    //거리 기준
    Page<Post> findAllByDistanceAndEndDateGreaterThanOrderByRunningDateAsc(Pageable pageable, String distance, LocalDate today);
    //타입 기준
    Page<Post> findAllByTypeAndEndDateGreaterThanOrderByRunningDateAsc(Pageable pageable, String type, LocalDate today);

}
