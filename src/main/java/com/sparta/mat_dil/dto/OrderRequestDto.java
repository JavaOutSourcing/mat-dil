package com.sparta.mat_dil.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    List<Long> foodId;
}
