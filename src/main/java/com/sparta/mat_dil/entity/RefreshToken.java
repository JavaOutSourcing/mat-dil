package com.sparta.mat_dil.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class RefreshToken {

    @Id
    private String token;

    @ManyToOne
    private User user;

    private LocalDateTime expiryDate;
}
