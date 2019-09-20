package com.example.userAuthAPI.config;

import com.example.userAuthAPI.support.JWTAuthenticationFilter;
import com.example.userAuthAPI.support.JWTAuthorizationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.userAuthAPI.config.Mappings.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager(), bCryptPasswordEncoder());
		filter.setFilterProcessesUrl(LOGIN_URL);

		http.cors()
			.and().authorizeRequests()
			.antMatchers("/public", SIGNUP_URL, LOGIN_URL).permitAll()
			.anyRequest().authenticated()
			.and().logout()
			.and().csrf().disable()
			.addFilter(filter)
			.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
