
package com.example.demo.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.jwtutl.JwtUtil;
import com.example.demo.service.CustomUserservice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserservice customuserservice;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		
		
//		String path = request.getRequestURI();
//
//	    // ✅ SKIP JWT FOR PUBLIC APIs
//	    if (path.equals("/Api/login") ||
//	        path.equals("/Api/signup") ||
//	        path.equals("/Api/check-user") ||
//	        path.equals("/Api/vendor-signup") ||
//            path.equals("/delivery-signup")  ||
//	         path.equals("/foods/getallcustomer-facingmenu" ) )
//	        {
//
//	        filterChain.doFilter(request, response);
//	        return;
//	    }
		// Step 1: Get token from header
		String authheader = request.getHeader("Authorization");

		// Step 2: Skip if no token
		if (authheader == null || !authheader.startsWith("Bearer ")) {
		    filterChain.doFilter(request, response);
		    return; // ✅ VERY IMPORTANT
		}

		// Step 3: Extract token
		String token = authheader.substring(7);
		
		
//  step 4 username extract 
	String usernamefromtoken = jwtUtil.getuserName(token);
	
	
//	 step 5 load user from db
	
	UserDetails userdetails = customuserservice.loadUserByUsername(usernamefromtoken);
	
//  step 6 validate token and create object of authentication 
	
	if(jwtUtil.isvalidToken( userdetails, token)) {
		
//	step 7  create authentication object 
		UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
//		set in securitycontxtholder
		SecurityContextHolder.getContext().setAuthentication(authtoken);
		
	}
	
	

	// Step 9 — pass to next filter/controller
	filterChain.doFilter(request, response);
	
	
	
	}
	
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) {
//	    String path = request.getServletPath();
//
//	    return path.equals("/foods/getallcustomer-facingmenu" )  || path.equals("/Api/check-user");
//	}
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    String path = request.getServletPath();

	    return path.equals("/Api/login") ||
	           path.equals("/Api/signup") ||
	           path.equals("/Api/check-user") ||
	           path.equals("/Api/send-otp") ||         
	           path.equals("/Api/verify-otp") ||
	           path.equals("/Api/vendor-signup") ||
	           path.equals("/delivery-signup") ||
	           path.equals("/foods/getallcustomer-facingmenu")||
	           path.equals("/Api/register-guest")  
	           ; 
	}

}
