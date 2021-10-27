package com.project.joopging.service;

import com.project.joopging.dto.comment.CommentCreateRequestDto;
import com.project.joopging.dto.comment.CommentUpdateRequestDto;
import com.project.joopging.exception.CustomErrorException;
import com.project.joopging.model.Comment;
import com.project.joopging.model.Post;
import com.project.joopging.model.User;
import com.project.joopging.repository.CommentRepository;
import com.project.joopging.repository.PostRepository;
import com.project.joopging.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(User user, CommentCreateRequestDto requestDto) {
        Long postId = requestDto.getPostId();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomErrorException("게시글을 찾을 수 없습니다")
        );

        Comment comment = Comment.of(requestDto, user, post);

                // fetch Lazy 유저를 진짜 유저로 변환
        Long userId = user.getId();
        User writer = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저 정보를 찾을 수 없습니다")
        );

        //Post 테이블에도 Comment 추가 (양방향)
        List<Comment> comments = post.getComments();
        comments.add(comment);

        //User 테이블에도 Comment 추가 (양방향)
        List<Comment> commentList = writer.getComment();
        commentList.add(comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void update(User user, CommentUpdateRequestDto requestDto, Long commentId) {
        Long postId = requestDto.getPostId();
        Post post = postRepository.findById(postId).orElseThrow(
                () ->new CustomErrorException("게시글을 찾을 수 없습니다")
        );
        Comment comment = getCommentById(commentId);
        if (comment.isWrittenBy(user)) {
            comment.update(requestDto);
        } else {
            throw new CustomErrorException("댓글 작성자가 아닙니다");
        }
    }

    public void delete(User user, Long commentId) {
        Comment comment = getCommentById(commentId);
        if (comment.isWrittenBy(user)) {
            commentRepository.delete(comment);
        } else {
            throw new CustomErrorException("댓글 작성자가 아닙니다");
        }
    }


    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () ->new CustomErrorException("댓글을 찾을 수 없습니다.")
        );
    }


}