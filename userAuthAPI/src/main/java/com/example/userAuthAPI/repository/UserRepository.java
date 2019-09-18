package com.example.userAuthAPI.repository;

import com.example.userAuthAPI.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

}
