package com.project.joopging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer location;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private Integer distance;

    @Column(nullable = false)
    private String userImg;

    @OneToMany(mappedBy = "userJoin")
    private List<Join> join;

    @OneToMany(mappedBy = "writer")
    private List<Post> post;

    @OneToMany(mappedBy = "userReview")
    private List<Review> review;

    @OneToMany(mappedBy = "userComment")
    private List<Comment> comment;

}
