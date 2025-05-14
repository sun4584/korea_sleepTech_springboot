package com.example.korea_sleepTech_springboot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 기본 생성자를 protected로 생성
// : JPA에서 필요로 함
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id") // DB 컬럼 이름과 매핑
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @Builder
    public Role(String roleName) {
        this.roleName = roleName;
    }
}