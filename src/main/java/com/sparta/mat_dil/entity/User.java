package com.sparta.mat_dil.entity;
import com.sparta.mat_dil.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    public User(UserRequestDto requestDto) {
        this.accountId = requestDto.getAccountId();
        this.password = requestDto.getPassword();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.userType = requestDto.getUserType();
        this.userStatus = UserStatus.ACTIVE;
    }

    public void withdrawUser() {
        this.userStatus = UserStatus.DEACTIVATE;
    }
}
