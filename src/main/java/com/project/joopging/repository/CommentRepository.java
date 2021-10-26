package com.project.joopging.repository;

import com.project.joopging.dto.comment.CommentUpdateRequestDto;
import com.project.joopging.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
