package com.project.joopging.dto.reCommentDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AllReCommentResponseDto {

    private final Long reCommentId;
    private final LocalDateTime modifiedAt;
    private final Long userId;
    private final String nickname;
    private final String userImg;
    private final String content;
}
