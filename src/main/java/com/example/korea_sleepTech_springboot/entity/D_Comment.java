package com.example.korea_sleepTech_springboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "post")
public class D_Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Comment와 Post는 N:1의 관계
     *   : 여러 개의 Comment가 하나의 Post에 연결
     *
     * @ManyToOne
     *   : N:1의 관계를 정의
     *   : fetch = FetchType.LAZY - Comment를 조회할 때 Post가 즉시 로딩되지 않고, 필요할 때 로딩
     *
     * @JoinColumn
     *   : DB에서 외래 키 컬럼명을 명시!
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private D_Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String commenter;
}