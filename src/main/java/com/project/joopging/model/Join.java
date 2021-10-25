package com.project.joopging.model;

import com.project.joopging.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Join extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID",  nullable = false)
    User userJoin;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    Post postJoin;

    public Join(User userJoin, Post postJoin) {
        this.userJoin = userJoin;
        this.postJoin = postJoin;
    }
}