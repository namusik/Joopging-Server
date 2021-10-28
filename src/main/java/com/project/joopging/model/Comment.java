package com.project.joopging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.comment.CommentCreateRequestDto;
import com.project.joopging.dto.comment.CommentUpdateRequestDto;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "댓글 정보")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "댓글 아이디")
    Long id;

    @Column(nullable = false)
    @ApiModelProperty(value = "댓글 내용")
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    User userComment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "POST_ID", nullable = false)
    @ApiModelProperty(value = "게시글 정보")
    Post postComment;

    public Comment(String content, User userComment, Post postComment) {
        this.content = content;
        this.userComment = userComment;
        this.postComment = postComment;
    }

    public Comment(CommentCreateRequestDto requestDto, User user, Post post){
        this.content = requestDto.getContent();
        this.userComment = user;
        this.postComment = post;
    }

    public static Comment of(CommentCreateRequestDto requestDto, User user, Post post) {
        return new Comment(requestDto, user, post);
    }
    public boolean isWrittenBy(User user) {
        return this.userComment.getId().equals(user.getId());
    }

    public void update(CommentUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
