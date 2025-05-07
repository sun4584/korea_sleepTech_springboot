package com.example.korea_sleepTech_springboot.이론;

public class O_Validation {
    /*
     * === Spring Boot Validation ===
     * : 애플리케이션에서 사용자의 입력을 검증하기 위한 도구
     * : jakarta.validation 에서 사용
     *
     * cf) Validation: 확인, (유효성) 검정
     *
     * Spring Boot Validation 애너테이션 종류
     *
     * 1) @NotNull(message = "이름은 필수입니다.")
     *       : 값이 null이 아니어야 함
     *
     * 2) @NotEmpty(message = "내용을 입력해주세요.")
     *       : 값이 null이 아니고, 빈 문자열("")이 아닌 경우
     *
     * 3) @NotBlank(message = "공백만 입력할 수는 없습니다.")
     *       : 공백 문자열이어도 안됨
     *
     * 4) @Size(min = 5, max = 20)
     *       : 문자열, 배열, 리스트 등의 크기를 제한
     *
     * 5) @Min(0) / @Max(100)
     *       : 숫자가 최소, 최대 값 이상, 이하 임을 제한
     *
     * 6) @Email(message = "유효한 이메일 형식이 아닙니다.")
     *       : 올바른 이메일 형식임을 확인
     *
     * 7) @Pattern(정규표현식 사용)
     *       : 사용자 지정 형식에 맞는지 확인
     *
     * 8) @Positive / @PositiveOrZero (@Negative / @NegativeOrZero)
     *       : 양수(음수)만 허용 / 양수(음수) 또는 0을 허용
     *
     * 9) @Future / @Past
     *       : 미래 날짜만 허용 / 과거 날짜만 허용
     * */
}