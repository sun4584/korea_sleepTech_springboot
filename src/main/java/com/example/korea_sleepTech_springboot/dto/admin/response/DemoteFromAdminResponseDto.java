package com.example.korea_sleepTech_springboot.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class DemoteFromAdminResponseDto {
    private String email; // 권한이 변경된 사용자 email
    private List<String> roles; // 가지고 있는 권한
    private String message;
}