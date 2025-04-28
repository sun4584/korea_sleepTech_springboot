package com.example.korea_sleepTech_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// @SpringBootApplication
// : 스프링부트에 필요한 기본 설정을 제공

@SpringBootApplication
public class KoreaSleepTechSpringbootApplication {

	public static void main(String[] args) {
		// KoreaSleepTechSpringbootApplication.class
		// : 스프링부트3 애플리케이션의 메인 클래스로 사용할 클래스 정의
		// args
		// : 커맨드 라인의 인수들을 전달
		SpringApplication.run(KoreaSleepTechSpringbootApplication.class, args);
	}

}