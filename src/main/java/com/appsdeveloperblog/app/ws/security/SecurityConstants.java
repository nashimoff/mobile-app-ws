package com.appsdeveloperblog.app.ws.security;

import org.springframework.core.env.Environment;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;

public class SecurityConstants {
	
	public static final long EXPIRATION_TIME=864000000;  // 10 days
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String HEADER_STRING="Authorization";
	public static final String SIGN_UP_URL="/users";
	public static final String TOKEN_SECRET="k9m2xv7bq0n5r8t1s6d3f4g7h2j9k8l0p5w1e6r3t9y2u7i4o8a5s1d6f3g0h9j2"; // later will be explained

	public static String getTokenSecret() {
		Environment environment = (Environment) SpringApplicationContext.getBean("environment");
		return environment.getProperty("tokenSecret");
	}
}
