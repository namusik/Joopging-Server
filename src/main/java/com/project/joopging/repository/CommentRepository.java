package com.project.joopging.repository;

import com.project.joopging.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByReplyTo(Long commentId);
}
