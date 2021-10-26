package com.project.joopging.dto.comment;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateRequestDto {

    private final Long postId;
    private final String content;
}
