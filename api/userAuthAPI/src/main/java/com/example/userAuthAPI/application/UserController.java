package com.example.userAuthAPI.application;

import com.example.userAuthAPI.model.UserObject;
import com.example.userAuthAPI.service.UserService;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.userAuthAPI.config.Mappings.SIGNUP_URL;

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
		
		String email = (String) (authentication.getPrincipal());
		
		return userService.findByEmail(email);
	}

	@PostMapping(SIGNUP_URL)
	@ResponseStatus(HttpStatus.CREATED)
	public UserObject signupUser(@RequestBody @Validated UserObject user) {
		user.encrypt(bCryptPasswordEncoder);
		return userService.create(user);
	}
	
//	@DeleteMapping(path = "{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void deleteById(@PathVariable long id) {
//		userService.deleteById(id);
//	}
}
