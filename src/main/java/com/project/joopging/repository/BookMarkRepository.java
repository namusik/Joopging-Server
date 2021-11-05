package com.project.joopging.repository;

import com.project.joopging.model.BookMark;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByUserBookMarkAndPostBookMark(User userId, Post postId);

    List<BookMark> findAllByUserBookMark(User myUser);
}
