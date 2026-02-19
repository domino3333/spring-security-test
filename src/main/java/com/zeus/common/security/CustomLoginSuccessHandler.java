package com.zeus.common.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Log
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		log.info("onAuthenticationSuccess");
		User customUser = (User) authentication.getPrincipal();
		log.info("username = " + customUser.getUsername());
		log.info("username = " + customUser.getPassword());
		log.info("username = " + customUser.getAuthorities().toString());

		super.onAuthenticationSuccess(request, response, authentication);
	}
}
