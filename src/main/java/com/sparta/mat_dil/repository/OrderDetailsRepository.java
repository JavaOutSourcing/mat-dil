package com.sparta.mat_dil.repository;

import com.sparta.mat_dil.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
