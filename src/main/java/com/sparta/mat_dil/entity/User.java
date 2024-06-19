package com.sparta.mat_dil.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String intro;

    @Column
    private String refreshToken;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

}
