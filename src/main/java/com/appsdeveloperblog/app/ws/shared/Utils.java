package com.appsdeveloperblog.app.ws.shared;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.appsdeveloperblog.app.ws.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class Utils {
	
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public String generateUserId(int length) {
		return generateRandomString(length);
	}
	
	public String generateAddressrId(int length) {
		return generateRandomString(length);
	}
	
	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}
	
	public static boolean hasTokenExpired(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.getTokenSecret().getBytes());
			
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
			
			Date tokenExpirationDate = claims.getExpiration();
			Date todayDate = new Date();
			
			return tokenExpirationDate.before(todayDate);
			
		} catch (ExpiredJwtException ex) {
			System.out.println(">>> TOKEN EXPIRED: " + ex.getMessage());
			return true;
		} catch (Exception ex) {
			System.out.println(">>> TOKEN HATASI: " + ex.getMessage());
			return true;
		}
	}
	
	public String generateEmailVerificationToken(String userId) {
		SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.getTokenSecret().getBytes());
		
		String token = Jwts.builder()
				.setSubject(userId)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//				.signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret())
				.signWith(SignatureAlgorithm.HS384,SecurityConstants.getTokenSecret())
				.compact();
		return token;
	}
	
	public String generatePasswordResetToken(String userId)
	{
		String token = Jwts.builder()
				.setSubject(userId)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
//				.signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret())
				.signWith(SignatureAlgorithm.HS384,SecurityConstants.getTokenSecret())
				.compact();
		return token;
	}
}