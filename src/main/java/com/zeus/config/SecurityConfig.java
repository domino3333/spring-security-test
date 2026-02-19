package com.zeus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.zeus.common.security.CustomAccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
//@EnableWebSecurity: 스프링에서 지원하는 거 안 쓰고 이제 내가 커스텀만들겠다 선언
public class SecurityConfig {

	// 인증과 인가를 필터링할거임
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		log.info("----------------security config" + httpSecurity);

		// 1. csrf 토큰 비활성화
		httpSecurity.csrf(csrf -> csrf.disable());

		// 2. 인가 정책
		httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/board/list").permitAll() // 게시판 목록: 누구나
				.requestMatchers("/board/register").hasRole("MEMBER") // 게시판 등록: 회원만
				.requestMatchers("/notice/list").permitAll() // 공지사항 목록: 누구나
				.requestMatchers("/notice/register").hasRole("ADMIN") // 공지사항 등록: 관리자만
				.anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
		);

		// 3. 접근 거부 시 보여줄 페이지(예외 처리)
//		httpSecurity.exceptionHandling(exception ->exception.accessDeniedPage("/accessError"));
		httpSecurity.exceptionHandling(exception -> exception.accessDeniedHandler(createAccessDeniedHandler()));

		// 4. 기본 로그인 폼은 스프링시큐리티에서 제공하는 것을 쓰겠다
		httpSecurity.formLogin(Customizer.withDefaults());

		return httpSecurity.build();
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 지정된 아이디와 패스워드로 로그인이 가능하도록 설정한다.
		auth.inMemoryAuthentication().withUser("member").password("{noop}1234").roles("MEMBER");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN", "MEMBER");
	}

	// 3. 접근 거부 시 예외처리를 설정하는 클래스로 이동한다.
	@Bean
	public AccessDeniedHandler createAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

}
