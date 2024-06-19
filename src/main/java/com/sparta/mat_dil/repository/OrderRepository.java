package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
