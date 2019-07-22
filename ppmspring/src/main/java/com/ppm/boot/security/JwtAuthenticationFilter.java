package com.ppm.boot.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ppm.boot.domain.User;
import com.ppm.boot.services.CustomUserDetailsService;

import static com.ppm.boot.security.SecurityConstants.HEADER_STRING;
import static com.ppm.boot.security.SecurityConstants.TOKEN_PREFIX;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = getJWTTokenFromHeader(request);
		
		if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
			Long userId = jwtTokenProvider.getUserIdFromJwt(jwt);
			User user = customUserDetailsService.loadUserById(userId);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.EMPTY_LIST);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	@SuppressWarnings("unused")
	private String getJWTTokenFromHeader(HttpServletRequest request) {
		
		String token = request.getHeader(HEADER_STRING);
		
		if(StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
			return token.substring(7, token.length());
		}
		return null;
	}

	
}
