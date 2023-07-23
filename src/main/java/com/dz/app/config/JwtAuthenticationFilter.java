package com.dz.app.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dz.app.helper.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsServiceImpl customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// get Jwt token
		// is start with Bearer
		// validate token
		
		String requestHeader = request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		
		// check null and format
		if(requestHeader!=null && requestHeader.startsWith("Bearer ")) {
			
			jwtToken=requestHeader.substring(7);
			
			try {
				
				username = this.jwtUtil.extractUsername(jwtToken);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			// security code
			if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				// validate token 
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else {
				System.out.println("Token is not validated..");
			}
		}
			
		filterChain.doFilter(request, response);
	}

}
