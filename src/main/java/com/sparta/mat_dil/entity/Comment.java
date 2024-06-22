package com.sparta.mat_dil.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long likes = 0L;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes;

    @Builder
    public Comment(User user, Restaurant restaurant, String description) {
        this.user = user;
        this.restaurant = restaurant;
        this.description = description;
    }

    public void update(String description){
        this.description = description;
    }

    public Long updateLike(boolean isLike){
        if(isLike){this.likes += 1;}
        else{this.likes -= 1;}
        return this.likes;
    }
}
