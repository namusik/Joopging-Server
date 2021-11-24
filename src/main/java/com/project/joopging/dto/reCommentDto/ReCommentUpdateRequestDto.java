package com.project.joopging.dto.reCommentDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReCommentUpdateRequestDto {

    private final Long postId;
    private final Long commentId;
    private final Long reCommentId;
    private final String content;
}
