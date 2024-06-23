package com.sparta.mat_dil.entity;

import com.sparta.mat_dil.dto.RestaurantRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long likes = 0L;

    @Column
    private Boolean pinned;

    public Restaurant(User loginUser, RestaurantRequestDto requestDto) {
        this.user = loginUser;
        this.restaurantName = requestDto.getRestaurantName();
        this.description = requestDto.getDescription();
    }

    public void update(RestaurantRequestDto requestDto) {
        this.restaurantName = requestDto.getRestaurantName();
        this.description = requestDto.getDescription();
    }

    public Long updateLike(boolean islike){
        if(islike){this.likes += 1;}
        else{this.likes -= 1;}
        return this.likes;
    }
}
