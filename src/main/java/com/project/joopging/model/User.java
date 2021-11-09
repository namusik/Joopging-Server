package com.project.joopging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.joopging.dto.user.LoginDetailReponseDto;
import com.project.joopging.enums.UserRoleEnum;
import com.project.joopging.util.Timestamped;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "유저 정보")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "유저 PK")
    private Long id;

    @Column(unique = true)
    @ApiModelProperty(value = "유저 닉네임")
    private String nickname;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "유저 패스워드")
    private String password;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "유저 아이디")
    private String email;

    private Long socialId;

    @Column(nullable = false)
    @ApiModelProperty(value = "유저 선호지역")
    private String location;

    @Column(nullable = false)
    @ApiModelProperty(value = "유저 선호지형")
    private String type;

    @Column(nullable = false)
    @ApiModelProperty(value = "유저 선호거리")
    private String distance;

    @Column
    @ApiModelProperty(value = "유저 이미지")
    private String userImg;

    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(value = "유저 권한")
    private UserRoleEnum role;

    @Column
    @JsonIgnore
    @ApiModelProperty(value = "유저 자기소개")
    private String intro;


    @OneToMany(mappedBy = "userJoin", orphanRemoval = true)
    @JsonIgnore
    @ApiModelProperty(value = "참가자 정보")
    private List<Crew> crews;

    @OneToMany(mappedBy = "writer")
    @JsonIgnore
    @ApiModelProperty(value = "게시글 정보")
    private List<Post> post;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    @ApiModelProperty(value = "게시글 정보")
    private List<Campaign> campaigns;

    @OneToMany(mappedBy = "userReview")
    @JsonIgnore
    @ApiModelProperty(value = "후기 정보")
    private List<Review> review;

    @OneToMany(mappedBy = "userComment")
    @JsonIgnore
    @ApiModelProperty(value = "댓글 정보")
    private List<Comment> comment;

    @OneToMany(mappedBy = "userBookMark", orphanRemoval = true)
    @JsonIgnore
    @BatchSize(size = 50)
    @ApiModelProperty(value = "북마크 정보")
    private List<BookMark> bookMarks = new ArrayList<>();

    public User(String username, String password, String email, UserRoleEnum role, String enumLocation, String enumType, String enumDistance) {
        this.nickname = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.type = enumType;
        this.distance = enumDistance;
        this.location = enumLocation;
        this.intro = null;
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
        this.intro = null;
        this.userImg = null;
    }

    public LoginDetailReponseDto toBuildDetailUser() {
        return LoginDetailReponseDto.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
//                .password(this.password)
                .location(this.location)
                .type(this.type)
                .distance(this.distance)
                .userImg(this.userImg)
                .role(this.role)
                .intro(this.intro)
                .build();
    }


}
