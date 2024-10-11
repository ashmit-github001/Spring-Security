package com.ashmitagarwal.spring_security_lvl1.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


/* 
   This class loads first and register the security filter chain,
   as would be provided by the security config (or the default one - springSecurityFilterChain)
   ORs
   Delegates the filter chain (using Delegating Filter Proxy)
*/

public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	// Need to pass the configuration class to the super class (This is not needed in Spring version < 6.1)
	public WebSecurityInitializer() {
		super(MySecurityConfig.class);
	}
}
