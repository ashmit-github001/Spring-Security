package com.ashmitagarwal.spring_security_lvl2.controllers;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	InMemoryUserDetailsManager inMemoryUserDetailsManager;
	
	PasswordEncoder passwordEncoder;
	
	// Constructor injection is preferred over @Autoired?
	HomeController(InMemoryUserDetailsManager inMemoryUserDetailsManager, PasswordEncoder passwordEncoder){
		
		this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello!!!!";
	}
	
	@GetMapping("/hi")
	public String hi() {
		return "Hi!!!!";
	}
	
	@GetMapping("/bye")
	public String bye() {
		return "Bye!!!!";
	}
	
	@GetMapping("/register")
	public String register(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		if(username != null && password != null) {
			
			UserDetails user = User
					.withUsername(username)
					.password(passwordEncoder.encode(password))
					.roles("USER")
					.build();
			
			this.inMemoryUserDetailsManager.createUser(user);
			
			return "You are successfully registered " + username;			
					
		}
		else
			return "Invalid details";
	}
}
