package com.project.joopging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.reCommentDto.AllReCommentResponseDto;
import com.project.joopging.dto.reCommentDto.ReCommentCreateRequestDto;
import com.project.joopging.dto.reCommentDto.ReCommentUpdateRequestDto;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "대댓글 정보")
public class ReComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "대댓글 아이디")
    Long id;

    @Column(nullable = false,length = 1000)
    @ApiModelProperty(value = "대댓글 내용")
    String content;


    //양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    User userReComment;

    //양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "POST_ID", nullable = false)
    @ApiModelProperty(value = "게시글 정보")
    Post postReComment;

    //양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    @ApiModelProperty(value = "댓글 정보")
    Comment commentReComment;

    public ReComment(
            ReCommentCreateRequestDto requestDto, User userReComment,
            Post postReComment, Comment commentReComment
    ) {
        this.content = requestDto.getContent();
        this.userReComment = userReComment;
        this.postReComment = postReComment;
        this.commentReComment = commentReComment;
    }

    public static ReComment of(
            ReCommentCreateRequestDto requestDto, User userReComment,
            Post postReComment, Comment commentReComment
    ) {
        return new ReComment(requestDto,userReComment,postReComment,commentReComment);
    }

    public void update(ReCommentUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public boolean isWrittenBy(User user) {
        return this.userReComment.getId().equals(user.getId());
    }

    public AllReCommentResponseDto toBuildDetailReComment(Long reCommentId, LocalDateTime reModifiedAt, Long reUserId, String reNickname,
                                                          String reUserImg,
                                                          String reContent) {
        return AllReCommentResponseDto.builder()
                .reCommentId(reCommentId)
                .modifiedAt(reModifiedAt)
                .userId(reUserId)
                .nickname(reNickname)
                .userImg(reUserImg)
                .content(reContent)
                .build();
    }
}
