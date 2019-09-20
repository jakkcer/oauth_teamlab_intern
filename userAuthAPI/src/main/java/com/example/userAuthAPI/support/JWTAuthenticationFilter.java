package com.example.userAuthAPI.support;

import com.example.userAuthAPI.model.UserObject;
import com.example.userAuthAPI.config.SecurityConstants;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.userAuthAPI.config.Mappings.LOGIN_URL;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private SecurityConstants prop;
	
	private AuthenticationManager authenticationManager;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public JWTAuthenticationFilter(AuthenticationManager manager, BCryptPasswordEncoder encoder) {
		this.authenticationManager = manager;
		this.bCryptPasswordEncoder = encoder;
		
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_URL, "POST"));
		
		setUsernameParameter("user");
		setPasswordParameter("password");
		setFilterProcessesUrl(LOGIN_URL);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
												HttpServletResponse res) throws AuthenticationException {
		
		try {
			UserObject userObject = new ObjectMapper().readValue(req.getInputStream(), UserObject.class);
			
			return this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							userObject.getName(),
							userObject.getPassword(),
							new ArrayList<>())
					);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		String token = Jwts.builder()
				.setSubject(((User)auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + prop.getExpirationTime()))
				.signWith(SignatureAlgorithm.HS256, prop.getSecret().getBytes())
				.compact();
		res.addHeader(prop.getHeaderString(), prop.getTokenPrefix() + token);
	}
}
