package com.example.korea_sleepTech_springboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_change_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoleChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String email;

    @Lob // TEXT 타입 명시
    private String prevRoles;
    @Lob
    private String newRoles;

    private String changedBy;
    private String changeType; // ADD, REMOVE, UPDATE
    private String changeReason;

    @Column(updatable = false)
    private LocalDateTime changedAt;

    @PrePersist // Entity 영속화되기 전에 실행 - 엔티티의 상태가 DB에 저장되기 전에 동작이 수행되도록 지정
    public void prePersist() {
        this.changedAt = LocalDateTime.now(); // 등록 시점 자동 기록
    }
}