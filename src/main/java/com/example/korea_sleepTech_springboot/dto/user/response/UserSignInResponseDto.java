package com.example.korea_sleepTech_springboot.dto.user.response;

import com.example.korea_sleepTech_springboot.entity.User;

public class UserSignInResponseDto {
    private String token; // jwt 토큰
    private User user;
    private int exprTime; // expire + time: (토큰) 만료 시간
}