package com.zeus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
//@EnableWebSecurity: 스프링에서 지원하는 거 안 쓰고 이제 내가 커스텀만들겠다 선언
public class SecurityConfig {
	
	// 인증과 인가를 필터링할거임
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		log.info("----------------security config"+http);
		
		//1. csrf 토큰 비활성화
		http.csrf(csrf->csrf.disable());
		//2. 기본 로그인 폼은 스프링시큐리티에서 제공하는 것을 쓰겠다
		http.formLogin(Customizer.withDefaults());
		
		return null;
	}
}
