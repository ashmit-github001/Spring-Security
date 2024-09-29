package com.ashmitagarwal.spring_security_deprecated.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* 
   This class is made to create the filter chain bean,
   which would be registered by the WebInitizalizer class
*/

/*
   WebSecurityConfigurerAdapter is DEPRECATED from spring security - 5.7.0-M2 onwards
*/


// @Configuration - Already included as a part of @EnableWebSecurity
@EnableWebSecurity // (debug = true) // Do not use debug mode in Production
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

}
