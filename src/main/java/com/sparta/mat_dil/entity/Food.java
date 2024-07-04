package com.sparta.mat_dil.entity;

import com.sparta.mat_dil.dto.FoodRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "food")
public class Food extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Food(Restaurant restaurant, FoodRequestDto foodRequestDto){
        this.foodName=foodRequestDto.getFoodName();
        this.price=foodRequestDto.getPrice();
        this.description= foodRequestDto.getDescription();
        this.restaurant=restaurant;
    }
    public void update(FoodRequestDto requestDto) {
        this.foodName = requestDto.getFoodName();
        this.price=requestDto.getPrice();
        this.description = requestDto.getDescription();
    }
}


