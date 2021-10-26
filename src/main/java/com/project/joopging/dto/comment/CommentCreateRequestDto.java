package com.project.joopging.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CommentCreateRequestDto {

    private final Long postId;
    private final String content;
}
