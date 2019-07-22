package com.ppm.boot.security;

import static com.ppm.boot.security.SecurityConstants.EXPIRATION_TIME;
import static com.ppm.boot.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ppm.boot.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTTokenProvider {
	
	public String generateToken(Authentication authentication) {
		
		User user = (User) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(System.currentTimeMillis()+EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", userId);
		claims.put("usernamme", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		return Jwts.builder().setSubject(userId)
							.setClaims(claims)
							.setIssuedAt(now)
							.setExpiration(expiryDate)
							.signWith(SignatureAlgorithm.HS512, SECRET)
							.compact();
		
		
		
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		}catch(SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			System.out.println("Error in validating token : "+e);
			return false;
		}
	}
	
	public Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String) claims.get("id");
		return Long.parseLong(id);
	}

}
