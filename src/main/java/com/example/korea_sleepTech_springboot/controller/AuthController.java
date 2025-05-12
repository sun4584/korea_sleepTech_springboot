package com.example.korea_sleepTech_springboot.controller;


import com.example.korea_sleepTech_springboot.common.ApiMappingPattern;
import com.example.korea_sleepTech_springboot.dto.user.response.UserSignInResponseDto;
import com.example.korea_sleepTech_springboot.dto.user.response.UserSignUpResponseDto;
import com.example.korea_sleepTech_springboot.service.AuthService;
import com.example.korea_sleepTech_springboot.시험.dto.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    // 1) 회원가입
    // - HTTP 메서드: POST
    // - URL 경로: /signup
    // @Params: UserSignUpRequestDto
    // @Return: UserSignUpResponseDto
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@Valid @RequestBody UserSignInResponseDto dto){

    }
}
