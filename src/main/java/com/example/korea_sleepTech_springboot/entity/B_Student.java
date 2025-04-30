package com.example.korea_sleepTech_springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // DB 테이블과 1:1 매핑, JPA 엔티티임을 선언(반드시 PK 기본키가 필요)
@Table(name = "student") // name 옵션 - 클래스명과 테이블명이 다를 경우 반드시 명시
//@Table(
//        name = "student",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"name", "email"})
//        }
//)
// >>> name과 email 둘 다 같은 데이터가 동시에 있으면 안됨 (EX. (a, b) >> (c, b) 가능, (a, d) 가능, (a, b) 불가능)
@Getter
@Setter
@NoArgsConstructor
public class B_Student {
    @Id // 해당 필드가 테이블의 PK 기본키 임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 값을 자동 생성 - DB의 AUTO_INCREMENT 사용
    private Long id;

    // final 또는 @NonNull 사용 시 @RequiredConstructor 에서 매개변수로 지정
    private String name;

    // 엔티티의 UNIQUE 제약 조건
    // 1) 컬럼 단위 명시*
    @Column(unique = true) // JPA가 테이블을 만들 때 email 컬럼에 UNIQUE 제약을 자동 설정
    private String email;

    // protected B_Student() {}
    // JPA는 엔티티 생성 시 기본 생성자를 사용 - 필수!

    public B_Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
}