package com.dz.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dz.app.config.CustomUserDetailsServiceImpl;
import com.dz.app.helper.JwtUtil;
import com.dz.app.model.JwtRequest;
import com.dz.app.model.JwtResponse;

@RestController
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsServiceImpl customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@RequestMapping(value = "/token" ,method = RequestMethod.POST) 
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		
		System.out.println(jwtRequest);
		
		try {
			
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
			
		} catch (UsernameNotFoundException ue) {
			ue.printStackTrace();
			System.out.println("User Not Found..");
			throw new Exception("User Not Found..");
		}catch (BadCredentialsException ce) {
			ce.printStackTrace();
			System.out.println("Bad Credentials.");
			throw new Exception("Bad Credentials..");
		}
		
		//if above code run ok means username and password authenticate then control come to this line
		
		//generate token 
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = jwtUtil.generateToken(userDetails);
		System.out.println("Jwt Token :"+token);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
}
