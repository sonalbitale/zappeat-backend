
package com.example.demo.jwtutl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;



@Component
public class JwtUtil {
	
	@Autowired
	private UserRepository userrepo;
	private String seceretKey = "mySmartFoodOrderingSecretKey12345678";
	
	 private final  Key  key = Keys.hmacShaKeyFor(seceretKey.getBytes());
	
	
	 public String generateToken(UserDetails userdetails) {

		    String role = userdetails.getAuthorities().stream()
		            .findFirst()
		            .map(GrantedAuthority::getAuthority)
		            .orElse("ROLE_USER");

		    return Jwts.builder()
		            .setSubject(userdetails.getUsername())
		            .claim("role", role)
		            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
		            .signWith(key, SignatureAlgorithm.HS256)
		            .compact();
		}
	  
	  
	  public Claims extractAllClaims(String token) {
		  return  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		  
	  }
	  
	  public Date getExpiration(String token) {
		  return extractAllClaims(token).getExpiration();
	  }
	  
	public String getuserName(String Token) {
		return extractAllClaims(Token).getSubject();
	}
	
	
	public boolean istokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
		
	}
	
//	 validate token 
	
	public boolean isvalidToken(UserDetails userdetails , String token) {
	
		 String extractedtoken = getuserName(token);
		 return (extractedtoken.equals(userdetails.getUsername()) && !istokenExpired(token));
	}
	
	
	  
	
	

}
