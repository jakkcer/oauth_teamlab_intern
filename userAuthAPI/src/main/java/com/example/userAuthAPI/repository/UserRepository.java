package com.example.userAuthAPI.repository;

import com.example.userAuthAPI.domain.UserObject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserObject, Long>{
	
	UserObject findByNameEquals(String name);
}
