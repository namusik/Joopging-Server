package com.project.joopging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.badge.MyBadgeListResponseDto;
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
@ApiModel(value = "뱃지 정보")
public class Badge extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "뱃지 아이디")
    Long id;

    @Column
    @ApiModelProperty(value = "뱃지 종류")
    int category;

    @Column
    @ApiModelProperty(value = "뱃지 레벨")
    int level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    User userBadge;


    public Badge (int category, int level) {
        this.category = category;
        this.level = level;
    }

    public MyBadgeListResponseDto toBuildBadge(String modifiedAtToString) {
        return MyBadgeListResponseDto.builder()
                .badgeId(this.id)
                .modifiedAt(modifiedAtToString)
                .badgeCategory(this.category)
                .badgeLevel(this.level)
                .build();
    }
}
