package com.example.korea_sleepTech_springboot.repository;

import com.example.korea_sleepTech_springboot.entity.D_Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<D_Post, Long> {

    // Spring Data JPA의 Query Method

    // === 1) 작성자 이름으로 게시물 조회 ===
    // findBy: SELECT * FROM D_POST
    // Author: WHERE author = ?
    // >> SELECT * FROM D_POST WHERE author = ?;

    // cf) 추가 조건
    //      - findByAuthorAndTitle(String author, String title);
    //      - findByAuthorOrTitle(String author, String title);
    //      - findByAuthorOrderByCreatedAtDesc(String author);
    //              >> 작성자 이름으로 조회 + 최신순으로 정렬
    // List<D_Post> findByAuthor(String author);

    // cf) @Query 애너테이션
    //      : 직접 JPQL이나 SQL을 작성해서 쿼리를 실행시켜주는 애너테이션
    //      : 쿼리 메서드로 표현하기 어려운 복잡한 조건을 처리할 때 사용 (EX. JOIN)
    //      +) D_Post - 엔티티 클래스 이름 기준! (테이블명 지정 시)
    //              >> 별칭 사용 권장
    //      +) :author - 메서드 파라미터를 매핑
//    @Query("SELECT p FROM D_Post p WHERE p.author = :author")
//    List<D_Post> findByAuthor(@Param("author") String author);

    // cf) Native SQL 사용 (진짜 SQL)
    @Query(value = "SELECT * FROM post WHERE author = :author", nativeQuery = true)
    List<D_Post> findByAuthor(@Param("author") String author);

    // === 2) 특정 키워드로 제목 검색 === //
    // List<D_Post> findByTitleContaining(String title);
    List<D_Post> findByTitleIgnoreCaseContaining(String title); // 대소문자 구분 없이 검색

//    @Query("SELECT p FROM D_Post p WHERE p.title LIKE %:keyword%")
//    List<D_Post> searchByTitle(@Param("keyword") String keyword);

    // === 3) 댓글이 가장 많은 상위 5개의 게시글 조회 === //
    @Query(value = """
                SELECT p.id, p.title, p.content, p.author, COUNT(c.id) AS commentCount
                FROM post p 
                    LEFT JOIN comment c 
                    ON p.id = c.post_id 
                GROUP BY p.id, p.title, p.content, p.author
                ORDER BY commentCount DESC 
                LIMIT 5
           """, nativeQuery = true)
    List<Object[]> findTop5ByOrderByCommentsSizeDesc(); // Object 클래스는 모든 클래스의 최상위 클래스
}