package com.project.joopging.dto.crew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrewAttendRequestDto {
    private Long postId;
    private List<Long> userId;
}
