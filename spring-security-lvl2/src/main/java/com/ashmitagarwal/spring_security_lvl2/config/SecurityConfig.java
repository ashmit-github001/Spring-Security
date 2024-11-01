package com.ashmitagarwal.spring_security_lvl2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

// Using Spring Security 6.3.3

@EnableWebSecurity(debug = true)
public class SecurityConfig {

	
	@Autowired 
	HttpSecurity httpSecurity;
	
	@Bean
	public SecurityFilterChain createFilterChain() throws Exception {
		
		// Using Spring Security 6.3.3
		
		
		/* Spring Security 6.1 onwards, authorizeHttpRequests() is deprecated, 
		   use the below overloaded version of method :
		*/

//		httpSecurity.authorizeHttpRequests(
//				new Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
//					
//					@Override
//					 public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry customizer) {
//						
//						customizer.anyRequest().authenticated();
//					}
//				})
//		.formLogin(Customizer.withDefaults());
		
		
		/* Also, From Spring Security 6.1 onwards, it is suggested to use the Lambda DSL way,
		   the above expression could be written as :
		*/
		
		httpSecurity.authorizeHttpRequests(customizer -> {
			
			customizer
			.requestMatchers(antMatcher("/register")).permitAll()
			
			// To permit access to the jsp files [in case used]
			.requestMatchers(antMatcher("/WEB-INF/views/**")).permitAll()
			
			.anyRequest().authenticated();
			
		})
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
		
		
		return httpSecurity.build();
		
	}
	
	
	@Bean
	public UserDetailsService manageUser() {
		
		return new InMemoryUserDetailsManager();
	}
	
	
	@Bean
	public PasswordEncoder createPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
