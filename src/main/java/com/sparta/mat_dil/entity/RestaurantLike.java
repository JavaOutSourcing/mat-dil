package com.sparta.mat_dil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class RestaurantLike extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private boolean Liked;

    public RestaurantLike(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
        this.Liked = false;
    }

    public void update() {
        this.Liked = !this.Liked;
    }
}
