package com.project.joopging.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel(value = "북마크 정보")
public class BookMark extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "북마크 아이디")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    @ApiModelProperty(value = "유저 정보")
    User userBookMark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "POST_ID")
    @ApiModelProperty(value = "게시글 정보")
    Post postBookMark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "CAMPAIGN_ID")
    @ApiModelProperty(value = "캠페인 정보")
    Campaign campaignBookMark;

    public BookMark(User userBookMark, Post postBookMark) {
        this.userBookMark = userBookMark;
        this.postBookMark = postBookMark;
    }

    public static BookMark of(User user, Post post) {
        return new BookMark(user, post);
    }

    public BookMark(User userBookMark, Campaign campaignBookMark) {
        this.userBookMark = userBookMark;
        this.campaignBookMark = campaignBookMark;
    }

    public static BookMark of(User user, Campaign campaign) {
        return new BookMark(user, campaign);
    }

}
