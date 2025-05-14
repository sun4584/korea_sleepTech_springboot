package com.example.korea_sleepTech_springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// : 기본 생성자 생성
//      - AccessLevel.PROTECTED를 통해 외부에서 new 키워드로 객체 생성 불가
//      - JPA는 기본 생성자가 반드시 필요, 외부에서 임의 생성 방지
@Getter
@Setter
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

    // 다대다 관계 매핑
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) // 엔티티를 영속화(저장)할 때 하위 엔티티도 같이 유지
    // cf) LAZY VS EAGER
    // LAZY(지연로딩): 해당 데이터와 해당 필드가 같이 사용되지 않는 경우
    // EAGER(즉시로딩): 해당 데이터와 해당 필드가 동시에 사용되는 경우
    @JoinTable(
            name = "user_roles", // 중간 테이블명
            joinColumns = @JoinColumn(name = "user_id"), // 현재 User 엔티티가 참조하는 FK
            inverseJoinColumns = @JoinColumn(name = "role_id") // 반대쪽 Role 엔티티의 FK
    )
    private Set<Role> roles;
    // Set 컬렉션 프레임워크: 저장 순서 X, 중복 저장 X

    @Builder
    public User(String email, String password, LocalDateTime createdAt, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    // ------------------------------------------------------------------ //
    // UserDetails 인터페이스 메서드 구현

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // authority(권한)
        // 사용자 권한을 반환하는 메서드
        // - GrantedAuthority: 인증된 사용자가 가지고 있는 권한을 표현
        // - EX) ROLE_USER, ROLE_ADMIN 등의 역할을 설정하여 반환

        // 해당 코드는 "user"라는 기본 권한만 설정
        // return List.of(new SimpleGrantedAuthority("user"));

        // 사용자의 Role을 Spring Security가 인식할 수 있도록 변환
        // EX) "USER" -> "ROLE_USER"
        return roles.stream()
                // 로그인 후 인증된 사용자의 권한을 Spring Security에 전달
                // : hasRole() 방식과 호환되기 위한 설정
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toSet());
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

    // 계정 만료 여부(true: 만료되지 않음)
    @Override public boolean isAccountNonExpired() {
        return true;
    }
    // 계정 잠김 여부(true: 잠기지 않음)
    @Override public boolean isAccountNonLocked() {
        return true;
    }
    // 인증 정보 만료 여부
    @Override public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정 활성화 여부
    @Override public boolean isEnabled() {
        return true;
    }
}