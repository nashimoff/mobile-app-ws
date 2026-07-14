package com.appsdeveloperblog.app.ws.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@EnableWebSecurity
public class WebSecurity {  ////

	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;

	public WebSecurity(UserService userService,
			BCryptPasswordEncoder bCryptPasswordEncoder,
			UserRepository userRepository) {
		this.userDetailsService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
		authenticationFilter.setFilterProcessesUrl("/users/login");

		http
			.cors().and()
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
				.antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL).permitAll()
				.antMatchers(SecurityConstants.H2_CONSOLE).permitAll()
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/h2-console/**").permitAll()
//				.antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.headers().frameOptions().disable()
			.and()
			.authenticationManager(authenticationManager)
			.addFilter(authenticationFilter)
			.addFilter(new AuthorizationFilter(authenticationManager, userRepository))
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}