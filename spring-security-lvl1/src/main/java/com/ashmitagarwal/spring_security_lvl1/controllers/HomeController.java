package com.ashmitagarwal.spring_security_lvl1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

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
}
