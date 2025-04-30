package com.example.korea_sleepTech_springboot.repository;

import com.example.korea_sleepTech_springboot.entity.A_TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// A extends(확장하다) B: A와 B가 동일 형태 (클래스-클래스, 인터페이스-인터페이스)
// C implements(구현하다) D: C는 클래스 / D는 인터페이스

// Repository
// : DB에 접근하는 객체 (DAO 형태)
// : DB에 읽고 쓰는 CRUD 작업을 담당하는 계층
// - JpaRepository를 상속받음 <엔티티타입, ID타입> 형태로 연결할 테이블을 명시

// cf) Entity는 테이블 자체, Repository는 그 테이블에 검색, 저장, 삭제 등의 작업을 수행

@Repository // 스프링이 해당 클래스를 Repository로 인식하도록 명시
public interface TestRepository extends JpaRepository<A_TestEntity, Long> {
}