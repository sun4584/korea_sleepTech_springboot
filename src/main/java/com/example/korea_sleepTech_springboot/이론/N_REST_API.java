
package com.example.korea_sleepTech_springboot.이론;
public class N_REST_API {
}
/*
 *
 * cf) "REST" API 학습 전 선행 키워드
 * 1) HTTP 프로토콜: 웹에서 클라이언트-서버 간 데이터를 주고받기 위한 통신 규약 (GET, POST, PUT, DELETE 등)
 * 2) URL(URI): 자원(Resource)을 표현하는 주소
 *       - REST에서 자원은 고유한 URI로 식별
 * 3) 요청(클라이언트) & 응답(서버)
 *
 * ===== REST(REpresentation State Transfer) API =====
 * - Representation: (자원의) 표현
 * - state: 상태
 * - transfer: 옮기다(이전하다)
 *
 * : 자원을 이름으로 표현하고, HTTP 메서드를 통해 자원을 처리하는 아키텍처 스타일
 * : 자원(resource)의 표현(representation)에 의한 상태 전달을 의미
 *
 * >> REST는 네트워크 상에서 클라이언트와 서버 사이의 통신 방식 중 하나
 *
 * cf) 어떤 자원에 대해 CRUD(Create, Read, Update, Delete) 연산을 수행하기 위해 URI(Resource)로
 *       GET, POST 등의 방식(Method)을 사용하여 요청을 보냄
 *       >> 해당 요청을 위한 자원은 특정 형태로 표현
 *
 * cf) URL: 인터넷 상 자원의 위치
 *       URI: 인터넷 상 자원을 식별하기 위한 문자열의 구성
 *   >> URI 안에 URL이 포함
 *
 * === RESTful API ===
 * : REST 아키텍처 스타일을 따르는 API를 의미
 *
 * 1. 명사 사용 (리소스명 또한 동사가 아닌 명사 사용)
 *   - URI는 명사로 표현!
 *   - URI에 동사 사용 X
 *   - HTTP 메서드는 목적에 맞게 사용
 *
 *   >> URI는 정보의 자원을 표현 (URI를 통해 얻고자 하는 정보를 표현)
 *   >> 자원에 대한 행위: URI 아니라 HTTP 메서드로 표현
 *
 * 2. 슬래시로 계층 관계를 표현
 *   - 해당 강사(user)의 강의 시간 검색
 *   GET /users/{userId}/schedule/{scheduleId}
 *
 * 3. URI의 마지막 문자로 슬래시(/) 포함 X
 *   GET /users/{userId}/schedules/{scheduleId}/ (X)
 *
 * 4. 밑줄(_) 사용 X, 하이픈(-) 사용 O
 *
 * 5. URI는 반드시 소문자로만 작성
 *
 * +) API 경로(URL)은 보통 복수형 형태로 작성하는 것이 표준
 *   /users/특정고유ID값
 *   /posts
 *   /books
 *   /menus
 * */

/*
 * ==== REST API 예시 =====
 *
 * 1) 사용자(User) 관련 REST API
 * - 사용자 목록 조회: GET /api/v1/users
 * - 특정 사용자 조회: GET /api/v1/users/{id}
 * - 사용자 등록: POST /api/v1/users
 * - 사용자 수정: PUT /api/v1/users/{id}
 * - 사용자 삭제: DELETE /api/v1/users/{id}
 *
 * 2) 쇼핑몰 관련 API
 * - 상품 목록: GET /api/v1/products
 * - 특정 상품의 리뷰: GET /api/v1/products/{productId}/reviews
 * - 특정 배송 방법의 주문 목록: GET /api/v1/deliveries/{deliveryId}/orders
 * */
