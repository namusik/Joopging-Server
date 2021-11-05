package com.project.joopging.dto.reCommentDto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ReCommentCreateRequestDto {

    private final Long postId;
    private final Long commentId;
    private final String content;
}
