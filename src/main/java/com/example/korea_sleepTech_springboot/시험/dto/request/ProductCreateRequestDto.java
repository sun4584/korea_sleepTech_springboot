package com.example.korea_sleepTech_springboot.시험.dto.request;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class ProductCreateRequestDto {
        private String name;
        private String description;
        private BigDecimal price;
    }

