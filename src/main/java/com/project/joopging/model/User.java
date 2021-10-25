package com.project.joopging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Location location;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private Distance distance;

    @Column
    private String userImg;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "userJoin")
    private List<Join> join;

    @OneToMany(mappedBy = "writer")
    private List<Post> post;

    @OneToMany(mappedBy = "userReview")
    private List<Review> review;

    @OneToMany(mappedBy = "userComment")
    private List<Comment> comment;

    public User(String username, String password, String email, UserRoleEnum role, Location enumLocation, Type enumType, Distance enumDistance) {
        this.nickname = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.type = enumType;
        this.distance = enumDistance;
        this.location = enumLocation;
        this.userImg = null;
    }
}
