package com.example.userAuthAPI.application;

import com.example.userAuthAPI.domain.UserObject;
import com.example.userAuthAPI.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.userAuthAPI.support.SecurityConstants.*;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/public")
	public List<UserObject> getUsers() {
		return userService.findAll();
	}
	
	@GetMapping("/private")
	@ResponseStatus(HttpStatus.OK)
	public UserObject getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = (String) (authentication.getPrincipal());
		
		return userService.findByName(username);
	}

	@PostMapping(SIGNUP_URL)
	@ResponseStatus(HttpStatus.CREATED)
	public UserObject postUser(@RequestBody @Validated UserObject user) {
		user.encrypt(bCryptPasswordEncoder);
		return userService.create(user);
	}
	
//	@GetMapping("/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	public Optional<UserObject> findById(@PathVariable("id") long id) {
//		return userService.findById(id);
//	}

//	@DeleteMapping(path = "{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void deleteById(@PathVariable long id) {
//		userService.deleteById(id);
//	}
}
