package com.project.joopging.model;

import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID",  nullable = false)
    private User userJoin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post postJoin;

    public Crew(User userJoin, Post postJoin) {
        this.userJoin = userJoin;
        this.postJoin = postJoin;
    }
}
