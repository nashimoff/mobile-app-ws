//package com.appsdeveloperblog.app.ws.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.appsdeveloperblog.app.ws.service.UserService;
//
//@EnableWebSecurity
//public class WebSecurity3 {
//	
//	private final UserService userDetailsService;
//	private final BCryptPasswordEncoder bCryptPasswordEncoder;
//	
//	public WebSecurity3(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//		this.userDetailsService = userService;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}
//	
//	@Bean
//	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
//	
//	// Configure AuthenticationManagerBuilder
//	AuthenticationManagerBuilder authenticationManagerBuilder = 	
//			http.getSharedObject(AuthenticationManagerBuilder.class);
//	
//	authenticationManagerBuilder
//	.userDetailsService(userDetailsService)
//	.passwordEncoder(bCryptPasswordEncoder);
//	
//	AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
//	
//	// Customize Login URL path
//	AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
//	authenticationFilter.setFilterProcessesUrl("/users/login");
//	
//	http
//	.cors().and()
//	.csrf().disable().authorizeRequests()
//	.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
//	.permitAll()
//	.antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
//	.permitAll()
//	.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
//	.permitAll()
//	.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
//	.permitAll()
////	.antMatchers("/error").permitAll()  // ← bunu ekle
//	.anyRequest().authenticated()
//	.and()
//	.authenticationManager(authenticationManager)
//	.addFilter(authenticationFilter)
//	.addFilter(new AuthorizationFilter(authenticationManager))
//	.sessionManagement()
//	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	
//	return http.build();
//	
//}
//	
//}
//	
//	