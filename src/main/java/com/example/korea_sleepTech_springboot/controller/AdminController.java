package com.example.korea_sleepTech_springboot.controller;

import com.example.korea_sleepTech_springboot.common.ApiMappingPattern;
import com.example.korea_sleepTech_springboot.dto.admin.request.PromoteToAdminRequestDto;
import com.example.korea_sleepTech_springboot.dto.admin.response.PromoteToAdminResponseDto;
import com.example.korea_sleepTech_springboot.dto.response.ResponseDto;
import com.example.korea_sleepTech_springboot.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.ADMIN_API)
public class AdminController {
    private final AdminService adminService;

    // == AdminController mapping pattern == //
    private static final String PUT_AUTHORITY_TO_ADMIN = "/promote";

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(PUT_AUTHORITY_TO_ADMIN)
    public ResponseEntity<ResponseDto<PromoteToAdminResponseDto>> promoteUserToAdmin(
            @RequestBody PromoteToAdminRequestDto dto
    ) {
        ResponseDto<PromoteToAdminResponseDto> response = adminService.promoteUserToAdmin(dto);
        return ResponseEntity.ok(response);
    }
}