package com.ashmitagarwal.spring_security_lvl2.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	
	WebSecurityInitializer(){
		super(SecurityConfig.class);
	}
}
