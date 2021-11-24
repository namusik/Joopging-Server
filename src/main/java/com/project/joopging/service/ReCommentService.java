//package com.project.joopging.service;
//
//import com.project.joopging.dto.reCommentDto.ReCommentCreateRequestDto;
//import com.project.joopging.dto.reCommentDto.ReCommentUpdateRequestDto;
//import com.project.joopging.exception.CustomErrorException;
//import com.project.joopging.model.Comment;
//import com.project.joopging.model.Post;
//import com.project.joopging.model.ReComment;
//import com.project.joopging.model.User;
//import com.project.joopging.repository.CommentRepository;
//import com.project.joopging.repository.PostRepository;
//import com.project.joopging.repository.ReCommentRepository;
//import com.project.joopging.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ReCommentService {
//
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//    private final ReCommentRepository reCommentRepository;
//    private final CommentRepository commentRepository;
//
//    @Transactional
//    public void createReComment(User user, ReCommentCreateRequestDto requestDto) {
//        Long postId = requestDto.getPostId();
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new CustomErrorException("게시글이 존재하지 않습니다.")
//        );
//        Long userId = user.getId();
//        User writer = userRepository.findById(userId).orElseThrow(
//                () -> new CustomErrorException("유저가 존재하지 않습니다")
//        );
//        Long commentId = requestDto.getCommentId();
//        Comment comment = commentRepository.findById(commentId).orElseThrow(
//                () -> new CustomErrorException("댓글이 존재하지 않습니다.")
//        );
//        ReComment reComment = ReComment.of(requestDto,user,post,comment);
//
//        reCommentRepository.save(reComment);
//        //Post,User,Comment 테이블에도 ReComment 추가 (양방향)
//        List<ReComment> postReComments = post.getReComments();
//        postReComments.add(reComment);
//        List<ReComment> userReComments = writer.getReComments();
//        userReComments.add(reComment);
//        List<ReComment> commentReComments = comment.getReComments();
//        commentReComments.add(reComment);
//    }
//
//    @Transactional
//    public void updateReComment(User user, ReCommentUpdateRequestDto requestDto) {
//        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
//                () -> new CustomErrorException("게시글이 존재하지 않습니다.")
//        );
//        Comment comment = commentRepository.findById(requestDto.getCommentId()).orElseThrow(
//                () -> new CustomErrorException("댓글이 존재하지 않습니다.")
//        );
//        ReComment reComment = getReCommentById(requestDto.getReCommentId());
//        if (reComment.isWrittenBy(user)) {
//            reComment.update(requestDto);
//        } else {
//            throw new CustomErrorException("댓글 작성자가 아닙니다.");
//        }
//
//    }
//
//    public void deleteReComment(User user, Long reCommentId) {
//        ReComment reComment = getReCommentById(reCommentId);
//        if (reComment.isWrittenBy(user)) {
//            reCommentRepository.delete(reComment);
//        } else {
//            throw new CustomErrorException("댓글 작성자가 아닙니다");
//        }
//    }
//
//
//
//    private ReComment getReCommentById(Long reCommentId) {
//        return reCommentRepository.findById(reCommentId).orElseThrow(
//                () ->new CustomErrorException("댓글을 찾을 수 없습니다.")
//        );
//    }
//
//
//}
