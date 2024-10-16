package com.ashmitagarwal.spring_security_lvl1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


/* 
   This class is made to create the filter chain bean,
   which would be registered by the WebSecurityInitializer class
*/

/*
   Changes in new spring security :
 *  WebSecurityConfigurerAdapter abstract class is DEPRECATED from spring security - 5.7.0-M2 onwards

 *  UserDetails Interface (implemented by User) 
 	- to represent users
 	
 *  Collection of GrantedAuthoriy (implemented by SimpleGrantedAuthority) 
 	- collection of user roles
 	
 *  UserService Interface (extended by UserServiceManager interface 
 	which is implemented by InMemoryUserDetailsManager/JdbcUserDetailsManager) 
 	- to manage the users details
 	
 *  PasswordEncoder Interface (implemented by NoOpPasswordEncoder/...)
 	- to encrypt or not encrypt the password while it is stored in the memory
 		- NoOpPasswordEncoder : To not encypt the password [Deprecated]
 		- BCryptPasswordEncoder : To encrypt password using BCrypt
 	 
 *  User can also be built using UserBuilder's build function.
    UserBuilder is an internal class inside User class.
    The functions withUsername return a UserBuilder object.
    This UserBuilder object runs the other functions and build function, which returns UserDetails object.
    
 *  SPRING SECURITY (6.0.0) --used by--> SPRING BOOT (3.0)
 	SPRING SECURITY (6.1.0) --used by--> SPRING BOOT (3.1)
 	There are some differences between the two.
 	
 *  Here we have used, Spring Security 6.0.0
*/

@Configuration
@EnableWebSecurity(debug = true) // Do not use debug mode in Production
public class MySecurityConfig {

	
	@Bean
	public UserDetailsService createUserUsingUserBuilder() {
		
//		UserDetails user = User
//				.withUsername("ashmit")
//				.password("a@123")
//				.roles("admin", "user")
//				.build(); // This will use the PasswordEncoder from the bean (i.e. NoOp)
//		
		UserDetails user1 = User
				.withUsername("Ram")
				.password("{noop}r@123") // This will use the NoOp password encoder (but Password Encoder bean should be disabled or not used before)
				.roles("admin", "user")
				.build();
		
		UserDetails user2 = User
				.withUsername("Krishna")
				.password("{noop}k@123")  // This will use the NoOp password encoder (but Password Encoder bean should be disabled or not used before)
				.roles("admin", "user")
				.build();
		
		UserDetails user3 = User
				.withUsername("Shiva")
				.password("{bcrypt}$2a$10$B2hl8kdc.fP7Nrq8RK7VduSVCDpdonYEQKGUJW1WhedHuxo/i/f5O")  // s@123
				/* 
				 * This will use the BCrypt password encoder, (but Password Encoder bean should be disabled or not used before),
				 * we need to provide the encoded password here,
				 * user will provide the de-crypted password while login
				*/
				.roles("admin", "user")
				.build();
		
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2, user3);
		//inMemoryUserDetailsManager.createUser(user);
		
		return inMemoryUserDetailsManager;
	}


	@Autowired
	HttpSecurity httpSecurity;
	
	
	// Overriding the default spring security filter chain
	@Bean
	SecurityFilterChain createFilter() throws Exception {
		
		// SPRING SECURITY 6.0.0
		
		httpSecurity.formLogin(); 
		// Adds these filters :   
				// UsernamePasswordAuthenticationFilter 
				// DefaultLoginPageGeneratingFilter 
				// DefaultLogoutPageGeneratingFilter
		// [Does not authorize/authenticate or show login page by default (AuthorizationFilter must be added for this to work)]
		
		
		httpSecurity.httpBasic(); 
		// Adds this filter : 
				// BasicAuthenticationFilter
		// [Used to authenticate requests using basic auth (AuthorizationFilter must be added for this to work)]
		
		
		 httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
		// Adds this filter : 
				// AuthorizationFilter
		// [To activate authentication of all requests (formLogin filters must be added)]
		
		
		// Would permit all requests without any authentication
//		 httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
		
		
		// Would deny all requests even after asking for credentials
//		 httpSecurity.authorizeHttpRequests().anyRequest().denyAll();
		
		
		// /hello -> authenticate , /hi -> permit , /bye -> deny
//		httpSecurity.authorizeHttpRequests().requestMatchers("/hello").authenticated();
//		httpSecurity.authorizeHttpRequests().requestMatchers("/hi").permitAll();
//		httpSecurity.authorizeHttpRequests().requestMatchers("/bye").denyAll();
		
		
		// Permit Mutiple requests
//		httpSecurity.authorizeHttpRequests().requestMatchers("/hello", "/hi").permitAll();
		
		
		// Deny Mutiple requests
//		httpSecurity.authorizeHttpRequests().requestMatchers("/hello", "/hi").denyAll();
		
		
		
		/* To use "requestMatchers()" without creating "mvcHandlerMappingIntrospector" bean :
		 
		   We will need to use AntPathRequestMatcher.antMatcher(String path), so
		   we can : import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
		   
		   OR
		   
		   Directly use antMatcher(String path), since it is a static method, so
		   we can : import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;
		*/
//		httpSecurity.authorizeHttpRequests().requestMatchers(antMatcher("/hello"), antMatcher("/hi")).permitAll();
//		httpSecurity.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/bye")).denyAll();
		
		
		return httpSecurity.build();
	}
	
	
	// This bean is needed for using "requestMatchers(String...)" [Not needed in Spring Boot]
	// OR use antMatcher and don't use this bean
	@Bean(name = "mvcHandlerMappingIntrospector")
	public HandlerMappingIntrospector HandlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}
	
	
//	@Bean
//	public UserDetailsService createUser() {
//
//		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//
//		authorities.add(new SimpleGrantedAuthority("admin"));
//		authorities.add(new SimpleGrantedAuthority("user"));
//
//		User user = new User("ashmit", "a@123", authorities);
//
//		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//		// InMemoryUserDetailsManager - Stores user details temporarily in memory
//		inMemoryUserDetailsManager.createUser(user);
//
//		return inMemoryUserDetailsManager;
//	}

//	@Bean
//	public PasswordEncoder encryptPassword() {
//
//		// Since, we do not wish to encrypt the password while storing in memory
//		return NoOpPasswordEncoder.getInstance();
//	}
}
