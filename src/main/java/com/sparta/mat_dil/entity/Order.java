package com.sparta.mat_dil.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {
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
    private int total_price;

    public void setTotalPrice(int totalPrice) {
        this.total_price = totalPrice;
    }

    public Order (User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public void sumPrice(int price) {
        this.total_price += price;
    }
}
