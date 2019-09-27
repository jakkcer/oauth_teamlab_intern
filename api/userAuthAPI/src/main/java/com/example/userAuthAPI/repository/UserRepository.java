package com.example.userAuthAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userAuthAPI.model.UserObject;

public interface UserRepository extends JpaRepository<UserObject, Long>{
	
	UserObject findByNameEquals(String name);
	UserObject findByEmailEquals(String email);
}
