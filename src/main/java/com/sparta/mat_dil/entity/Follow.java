package com.sparta.mat_dil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //팔로우 건 사람
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    //팔로우 받는 사람
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Column(nullable = false)
    private boolean followed;

    public Follow(User followedUser, User user) {
        this.fromUser=user;
        this.toUser=followedUser;
        this.followed = false;
        this.toUser.calculFollowersCnt();
    }

    public void update() {
        this.followed = !this.followed;
    }

}
