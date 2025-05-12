package com.example.korea_sleepTech_springboot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// : 기본 생성자 생성
//      - AccessLevel.PROTECTED를 통해 외부에서 new 키워드로 객체 생성 불가
//      - JPA는 기본 생성자가 반드시 필요, 외부에서 임의 생성 방지
@Getter
public class User implements UserDetails {
    // UserDetails
    // : Spring Security의 UserDetails 인터페이스를 구현
    // - 사용자 인증 및 권한 정보를 제공하는 인터페이스
    // - Spring Security가 사용자 정보를 가져오고, 인증 및 인가 처리를 할 때 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false) // DB 컬럼명 설정, updatable = false로 수정 불가 설정
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // SQL이 datetime이 JAVA의 DateTime과 호환

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public User(String email, String password, LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    // ------------------------------------------------------------------ //
    // UserDetails 인터페이스 메서드 구현

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한을 반환하는 메서드
        // - GrantedAuthority: 인증된 사용자가 가지고 있는 권한을 표현
        // - EX) ROLE_USER, ROLE_ADMIN 등의 역할을 설정하여 반환

        // 해당 코드는 "user"라는 기본 권한만 설정
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        // 사용자 비밀번호를 반환하는 메서드
        return password;
    }

    @Override
    public String getUsername() {
        // 사용자의 고유 식별자를 반환하는 메서드
        // : 일반적으로 username을 반환 (해당 코드는 email 을 사용)
        // - Spring Security가 로그인 처리 시 이 값을 통해 사용자 조회
        return email;
    }
}