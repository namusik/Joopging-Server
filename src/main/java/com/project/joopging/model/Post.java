package com.project.joopging.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;



@Entity
@Getter
@Setter
@NoArgsConstructor

public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate runningDate;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer location;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private Integer distance;

    @Column(nullable = false)
    private int limitPeople;

    @Column(nullable = false)
    private int nowPeople;

    @Column
    private String postImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "USER_ID", nullable = false)
    private User writer;

    @OneToMany(mappedBy = "postJoin")
    @JsonIgnore
    @BatchSize(size = 50)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "postReview")
    @JsonIgnore
    @BatchSize(size = 50)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "postComment")
    @JsonIgnore
    @BatchSize(size = 50)
    private List<Comment> comments = new ArrayList<>();


    public Post()
}
