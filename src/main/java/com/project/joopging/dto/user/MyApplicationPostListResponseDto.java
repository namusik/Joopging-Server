package com.project.joopging.dto.user;


import com.project.joopging.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class MyApplicationPostListResponseDto {

    private final List<Post> myApplicationPostList;
}
