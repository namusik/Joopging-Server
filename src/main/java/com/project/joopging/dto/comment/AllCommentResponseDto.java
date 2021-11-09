package com.project.joopging.dto.comment;


import com.project.joopging.dto.reCommentDto.AllReCommentResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class AllCommentResponseDto {

    private final Long commentId;
    private final LocalDateTime modifiedAt;
    private final Long userId;
    private final String nickname;
    private final String userImg;
    private final String content;
    private final List<AllReCommentResponseDto> reCommentList;
}
