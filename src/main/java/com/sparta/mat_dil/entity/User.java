package com.sparta.mat_dil.entity;

import com.sparta.mat_dil.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String intro;

    @Column
    private String refreshToken;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @Column
    private Long kakaoId;

    @OneToMany(mappedBy = "fromUser")
    private List<Follow> followings;

    @OneToMany(mappedBy = "toUser")
    private List<Follow> followers;

    @Column(nullable = false)
    private Long followersCnt=0L;

    @Column(nullable = false)
    private Long commentLikes=0L;

    @Column(nullable = false)
    private Long restaurantLikes=0L;

    @Column(nullable = false)
    private Long totalLikes=0L;

    //로그인시 리프레시 토큰 초기화
    @Transactional
    public void refreshTokenReset(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void update(Optional<String> newPassword, Optional<String> name, Optional<String> intro) {
        this.password = newPassword.orElse(this.password);
        this.name = name.orElse(this.name);
        this.intro = intro.orElse(this.intro);
    }

    public User(UserRequestDto requestDto) {
        this.accountId = requestDto.getAccountId();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.userType = requestDto.getUserType();
        this.userStatus = UserStatus.ACTIVE;
    }

    public User(String accountId, String password, String name, String email, UserType userType, UserStatus userStatus, Long kakaoId) {
        this.accountId = accountId;
        this.password=password;
        this.name=name;
        this.email=email;
        this.userType=userType;
        this.userStatus=userStatus;
        this.kakaoId=kakaoId;
    }

    public void withdrawUser() {
        this.userStatus = UserStatus.DEACTIVATE;
    }

    public void logout(){
        this.refreshToken=null;
    }

    public void kakaoIdUpdate(Long kakaoId) {
        this.kakaoId=kakaoId;
    }

    //팔로워 +1
    public void calculFollowersCnt(){
        this.followersCnt+=1;
    }

    public void updateCommentLikesCnt(){
        this.commentLikes +=1;
    }

    public void updateRestaurantLikesCnt(){
        this.restaurantLikes +=1;
    }

}
