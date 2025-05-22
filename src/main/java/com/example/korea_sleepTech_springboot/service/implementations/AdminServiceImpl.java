package com.example.korea_sleepTech_springboot.service.implementations;

import com.example.korea_sleepTech_springboot.common.ResponseMessage;
import com.example.korea_sleepTech_springboot.dto.admin.request.PutAuthorityRequestDto;
import com.example.korea_sleepTech_springboot.dto.admin.response.DemoteFromAdminResponseDto;
import com.example.korea_sleepTech_springboot.dto.admin.response.PromoteToAdminResponseDto;
import com.example.korea_sleepTech_springboot.dto.response.ResponseDto;
import com.example.korea_sleepTech_springboot.entity.Role;
import com.example.korea_sleepTech_springboot.entity.RoleChangeLog;
import com.example.korea_sleepTech_springboot.entity.User;
import com.example.korea_sleepTech_springboot.repository.RoleChangeLogRepository;
import com.example.korea_sleepTech_springboot.repository.RoleRepository;
import com.example.korea_sleepTech_springboot.repository.UserRepository;
import com.example.korea_sleepTech_springboot.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleChangeLogRepository roleChangeLogRepository;

    @Override
    @Transactional
    // 트랜잭션 처리를 선언적으로 해주는 Spring 기능
    // : 하나의 메서드 내의 모든 DB 작업을 하나의 트랜잭션으로 묶어줌
    public ResponseDto<PromoteToAdminResponseDto> promoteUserToAdmin(PutAuthorityRequestDto dto) {
        PromoteToAdminResponseDto data = null;

        // 1. 사용자 조회
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXISTS_USER));

        // 2. 권한 생성 또는 조회
        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().roleName("ADMIN").build()));

        // .anyMatch()
        // : 각 요소를 순회하면 조건에 부합하는 요소가 단 하나라도 존재하면 true 반환, 그렇지 않으면 false 반환
        boolean alreadyHasAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals("ADMIN"));

        if (alreadyHasAdmin) {
            throw new IllegalStateException("이미 ADMIN 권한을 가지고 있습니다.");
        }

        // cf) 로그 기록 작업 (이전 권한 목록 백업)
        String prevRoles = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(","));

        // 권한 추가
        user.getRoles().add(adminRole);

        // 3. 사용자 권한 수정 + 저장
        User savedUser = userRepository.save(user);

        List<String> roleList = savedUser.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        // 변경된 권한 목록 추출
        String newRoles = savedUser.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(","));

        // 로그 저장
        RoleChangeLog log = RoleChangeLog.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .prevRoles(prevRoles)
                .newRoles(newRoles)
                .changedBy(getCurrentAdminEmail())
                .changeType("ADD")
                .changeReason("관리자 승격")
                .build();

        roleChangeLogRepository.save(log);

        data = new PromoteToAdminResponseDto(
                user.getEmail(),
                roleList,
                "권한 변경이 정상적으로 이루어졌습니다."
        );

        // 4. 로그 저장

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<DemoteFromAdminResponseDto> demoteUserFromAdmin(PutAuthorityRequestDto dto) {
        DemoteFromAdminResponseDto data = null;

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 존재하지 않습니다."));

        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseThrow(() -> new IllegalArgumentException("ADMIN 권한이 존재하지 않습니다."));

        boolean hasAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals("ADMIN"));

        if (!hasAdmin) {
            throw new IllegalStateException("해당 사용자는 ADMIN 권한을 가지고 있지 않습니다.");
        }

        String prevRoles = user.getRoles().stream()
                .map(Role::getRoleName).collect(Collectors.joining(","));

        // 권한 회수
        user.getRoles().remove(adminRole);
        User savedUser = userRepository.save(user);

        List<String> newRoles = savedUser.getRoles().stream()
                .map(Role::getRoleName).collect(Collectors.toList());

        // 로그 저장
        RoleChangeLog log = RoleChangeLog.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .prevRoles(prevRoles)
                .newRoles(String.join(",", newRoles))
                .changedBy(getCurrentAdminEmail())
                .changeType("REMOVE")
                .changeReason("관리자 권한 회수")
                .build();

        roleChangeLogRepository.save(log);

        data = DemoteFromAdminResponseDto.builder()
                .email(savedUser.getEmail())
                .roles(newRoles)
                .message("ADMIN 권한이 성공적으로 회수되었습니다.")
                .build();

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    // 현재 로그인한 사용자의 이메일 또는 사용자명을 가져오는 함수
    private String getCurrentAdminEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // auth.getName()
        // : 토큰안에 저장된(로그인한 사용자의) username 또는 email을 반환
        return auth != null ? auth.getName() : "unknown";
    }
}