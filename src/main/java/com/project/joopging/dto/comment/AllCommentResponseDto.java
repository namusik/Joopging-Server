package com.project.joopging.dto.comment;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AllCommentResponseDto {

    private final Long commentId;
    private final String modifiedAt;
    private final Long userId;
    private final String nickname;
    private final String userImg;
    private final String content;
    private final Long replyTo;
}
