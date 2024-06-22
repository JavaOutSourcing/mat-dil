package com.sparta.mat_dil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class CommentLike extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private boolean Liked = false;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        this.Liked = false;
    }

    public void update() {
        this.Liked = !this.Liked;
    }
}
