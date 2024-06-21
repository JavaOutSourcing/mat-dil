package com.sparta.mat_dil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MatDilApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatDilApplication.class, args);
    }

}
