package com.project.joopging.model;

import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "참여자 정보")
public class Crew extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "크루 아이디")
    private Long id;
    
    @Column
    @ApiModelProperty(value = "출석 여부")
    private boolean attendation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID",  nullable = false)
    @ApiModelProperty(value = "유저 정보")
    private User userJoin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    @ApiModelProperty(value = "게시글 정보")
    private Post postJoin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIGN_ID")
    @ApiModelProperty(value = "게시글 정보")
    private Campaign campaignJoin;

    public Crew(User userJoin, Post postJoin) {
        this.userJoin = userJoin;
        this.postJoin = postJoin;
        this.attendation = false;
    }

    public void attend() {
        this.attendation = true;
    }

    //모임장 게시글 작성시 크루참여
    public static Crew of(User user, Post post) {
        return new Crew(user, post);
    }


    //캠페인에 크루 참여
    public Crew(User userJoin, Campaign campaignJoin) {
        this.userJoin = userJoin;
        this.campaignJoin = campaignJoin;
        this.attendation = false;
    }
    //모임장 게시글 작성시 크루참여
    public static Crew of(User user, Campaign campaign) {
        return new Crew(user, campaign);
    }
}
