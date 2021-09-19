package com.cognizant.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	private static Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("user")).roles("USER");
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() 
	{

		LOGGER.info("Start");
		
		return new BCryptPasswordEncoder();

	}
	
	
	@Override

	protected void configure(HttpSecurity httpSecurity) throws Exception {

	httpSecurity.csrf().disable().httpBasic().and()

	.authorizeRequests()

	
	.antMatchers("/menu-items-service/menu-items/").hasRole("ADMIN")
	
	.antMatchers("/cart-items-service/carts/").hasRole("USER")

	.antMatchers("/authenticate").hasAnyRole("ADMIN","USER")

	.anyRequest().authenticated()

	.and()

	.addFilter(new JwtAuthorizationFilter(authenticationManager()));

	}
}
