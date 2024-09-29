package com.ashmitagarwal.spring_security_deprecated.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;


/* 
   This class loads first and register the security filter chain,
   as would be provided by the security config (or the default one - springSecurityFilterChain)
*/

public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}
