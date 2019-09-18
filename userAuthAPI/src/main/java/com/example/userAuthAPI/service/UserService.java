package com.example.userAuthAPI.service;

import com.example.userAuthAPI.domain.User;
import com.example.userAuthAPI.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}
	
	public User create(User user) {
		return userRepository.save(user);
	}
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
}
