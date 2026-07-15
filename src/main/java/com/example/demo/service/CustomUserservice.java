
package com.example.demo.service;

import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;

@Service
public class CustomUserservice implements  UserDetailsService{
	
	@Autowired
	private UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	User user = 	 userrepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
	
	return new org.springframework.security.core.userdetails.User(
			user.getUsername(),   // tommy
            user.getPassword(),   // $2a$10$xyz (BCrypt hash)
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            );
			
	
  // ROLE_USER
    


	}

}
