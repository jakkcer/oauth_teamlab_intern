package com.example.userAuthAPI.service;

import com.example.userAuthAPI.domain.UserObject;
import com.example.userAuthAPI.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	public List<UserObject> findAll() {
		return userRepository.findAll();
	}

	public Optional<UserObject> findById(long id) {
		return userRepository.findById(id);
	}
	
	public UserObject findByName(String name) {
		return userRepository.findByNameEquals(name);
	}
	
	public UserObject create(UserObject user) {
		return userRepository.save(user);
	}
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserObject user = null;
		try {
			user = this.findByName(username);
			user.encrypt(passwordEncoder());
		} catch (Exception e) {
			throw new UsernameNotFoundException(username);
		}
		
		return User.withUsername(username)
				.password(user.getPassword())
				.authorities("ROLE_USER")
				.build();
	}
}
