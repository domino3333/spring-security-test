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
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		log.info("----------------security config"+httpSecurity);
		
		//1. csrf 토큰 비활성화
		httpSecurity.csrf(csrf->csrf.disable());

		
		//2. 인가 정책
		httpSecurity.authorizeHttpRequests(auth -> auth
	            .requestMatchers("/board/list").permitAll()          // 게시판 목록: 누구나
	            .requestMatchers("/board/register").hasRole("MEMBER") // 게시판 등록: 회원만
	            .requestMatchers("/notice/list").permitAll()         // 공지사항 목록: 누구나
	            .requestMatchers("/notice/register").hasRole("ADMIN") // 공지사항 등록: 관리자만
	            .anyRequest().authenticated()                         // 그 외 모든 요청은 인증 필요
	        );
		
		//3. 기본 로그인 폼은 스프링시큐리티에서 제공하는 것을 쓰겠다
		httpSecurity.formLogin(Customizer.withDefaults());
		
		return httpSecurity.build();
	}
}
