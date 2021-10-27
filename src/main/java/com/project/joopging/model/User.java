package com.project.joopging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.user.EditUserInfoDto;
import com.project.joopging.dto.user.LoginDetailReponseDto;
import com.project.joopging.enums.Distance;
import com.project.joopging.enums.Location;
import com.project.joopging.enums.Type;
import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @JsonIgnore
    private String email;

    private Long socialId;

    @Column
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    @Enumerated(EnumType.STRING)
    private Distance distance;

    @Column
    private String userImg;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "userJoin", orphanRemoval = true)
    @JsonIgnore
    private List<Party> join;

    @OneToMany(mappedBy = "writer")
    @JsonIgnore
    private List<Post> post;

    @OneToMany(mappedBy = "userReview")
    @JsonIgnore
    private List<Review> review;

    @OneToMany(mappedBy = "userComment")
    @JsonIgnore
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

    //소셜로그인용 유저
    public User( String email, String password, String nickname,  UserRoleEnum role, Long socialId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.socialId = socialId;
        this.role = role;
        this.type = null;
        this.distance = null;
        this.location = null;
        this.userImg = null;
    }

    public LoginDetailReponseDto toBuildDetailUser() {
        return LoginDetailReponseDto.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .location(this.location.getName())
                .type(this.type.getName())
                .distance(this.distance.getName())
                .userImg(this.userImg)
                .role(this.role)
                .build();
    }
}
