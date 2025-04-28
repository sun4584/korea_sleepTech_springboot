package com.example.korea_sleepTech_springboot.이론;

public class J_JpaRepository {
    // JpaRepository<E, ID>
    // : Spring Data JPA에서 제공하는 기본 인터페이스
    // - CRUD와 페이징, 정렬을 포함한 다양한 데이터 엑세스 메서드를 제공

    // 1. CRUD 메서드
    // ===== 메서드명(매개변수): 반환타입 =====

    // save(E entity): E
    // - 새로운 엔티티를 저장하거나, 기존 엔티티를 업데이트

    // 2) findById(ID id): E
    // - 주어진 ID에 해당하는 엔티티를 조회

    // 3) existsById(ID id): boolean
    // - 주어진 ID가 존재하는지 확인

    // 4) findAll(): List<E>
    // - DB에 있는 모든 엔티티를 조회

    // 5) deleteById(ID id): void
    // - 주어진 ID의 엔티티를 삭제

    // cf) E: 엔티티 클래스 타입, DB 테이블과 매핑되는 클래스
    // cf) ID: 엔티티의 기본키(PK)와 매핑되는 데이터
}
