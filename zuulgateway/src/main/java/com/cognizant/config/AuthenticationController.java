package com.cognizant.config;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController

public class AuthenticationController {
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	@GetMapping("/authenticate")
	public Map<String,String> authenticate(@RequestHeader("Authorization") String authHeader)
	{
		LOGGER.info("START");
		
		LOGGER.debug("authHeader:{}",authHeader);
		
		String user = getUser(authHeader);
		String token = generateJwt(authHeader);
		LOGGER.debug("user",user);
		LOGGER.debug("token:{}",token);
		Map<String,String> map = new HashMap<String, String>();
		map.put("token", token);
		
		LOGGER.info("END");
		
		return map;
	}
	
	private String getUser(String authHeader)
	{
		System.out.println("Auth Header"+authHeader);
		String encodedCredentials = authHeader.substring(6);
		System.out.println(encodedCredentials);
		byte[] decodedCredentials= Base64.getDecoder().decode(encodedCredentials);
		String decoded =  new String(decodedCredentials);
		System.out.println("decoded"+decoded);
		return decoded;
	}
	private String generateJwt(String user)
	{
		JwtBuilder builder = Jwts.builder();

		builder.setSubject(user);

		// Set the token issue time as current time

		builder.setIssuedAt(new Date());

		// Set the token expiry as 20 minutes from now

		builder.setExpiration(new Date((new Date()).getTime() + 1200000));

		builder.signWith(SignatureAlgorithm.HS256, "java");

		String token = builder.compact();

		return token;
	}

}
