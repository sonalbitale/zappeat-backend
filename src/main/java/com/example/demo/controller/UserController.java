package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginResponsedto;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.RequestDto;
import com.example.demo.dto.vendorsignupdto.VendorSignupDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.jwtutl.JwtUtil;
import com.example.demo.service.OtpService;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/Api")
public class UserController {
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private  JwtUtil jwtutil;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	
	
	@PostMapping("/check-user")
	public ResponseEntity<?> checkUser(@RequestBody Map<String, String> body) {

	    String phone = body.get("phone");

	    boolean exists = userservice.existsByPhone(phone);

	    return ResponseEntity.ok(Map.of("exists", exists));
	}
	
	
	@Autowired
	private OtpService otpService;

	@PostMapping("/send-otp")
	public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> body) {
	    String phone = body.get("phone");
	    if (phone == null || phone.length() != 10) {
	        return ResponseEntity.badRequest().body(Map.of("error", "Valid 10-digit phone number required"));
	    }
	    otpService.sendOtp(phone);
	    return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body) {
	    String phone = body.get("phone");
	    String otp = body.get("otp");

	    try {
	        otpService.verifyOtp(phone, otp);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
	    }

	    User user = userservice.findOrCreateByPhone(phone);

	    // Build UserDetails directly — bypasses loadUserByUsername since password may be null
	    UserDetails userdetails = new org.springframework.security.core.userdetails.User(
	        user.getUsername(),
	        "OTP_LOGIN_NO_PASSWORD",   // placeholder only, never checked or stored — generateToken() ignores this value entirely
	        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
	    );

	    String token = jwtutil.generateToken(userdetails);

	    return ResponseEntity.ok(Map.of(
	        "message", "Login successful",
	        "token", token,
	        "username", user.getUsername(),
	        "phone", user.getPhone()
	    ));
	}
	
	@PostMapping("/signup")
	
	public ResponseEntity<?> userSignUp(@RequestBody RegisterDTO registerdto){
		
		User user = new User();
        System.out.println("regs user"+registerdto.getUsername());
		user.setUsername(registerdto.getUsername());
		user.setEmailid(registerdto.getEmail());
		user.setPassword(passwordEncoder.encode(registerdto.getPassword()));
		user.setRole(Role.USER);
		userservice.saveUser(user);
		  
		  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User created successfully"));
	}
	
	
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody  RequestDto  requestdto) {
	try {
		Authentication authenticate = 	 authmanager.authenticate(new UsernamePasswordAuthenticationToken(requestdto.getUsername(), requestdto.getPassword()));
		 UserDetails userdetails = 	(UserDetails) authenticate.getPrincipal();
		 
		 String token =  jwtutil.generateToken(userdetails);
			
			return ResponseEntity.ok(new LoginResponsedto("login successfull", 200, token));

	} catch (BadCredentialsException e) {
		return ResponseEntity.status(401).body("invalid username");
			
}}
	   
	
	@PostMapping("/vendor-signup")
	public ResponseEntity<?> vendorSignUp(@RequestBody VendorSignupDTO dto) {
	    String message = userservice.vendorSignUp(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}
	
	
	
//	 guest register
	@PostMapping("/register-guest")
	public ResponseEntity<?> registerGuest(@RequestBody Map<String, String> body) {
	    String phone = body.get("phone");
	    String name = body.get("name");
	    String email = body.get("email");

	    if (phone == null || phone.length() != 10) {
	        return ResponseEntity.badRequest().body(Map.of("error", "Valid phone number required"));
	    }
	    if (name == null || name.isBlank()) {
	        return ResponseEntity.badRequest().body(Map.of("error", "Name is required"));
	    }
	    if (userservice.existsByPhone(phone)) {
	        return ResponseEntity.badRequest().body(Map.of("error", "An account with this phone number already exists"));
	    }

	    User user = new User();
	    user.setPhone(phone);
	    user.setUsername(name);
	    user.setEmailid(email);
	    user.setPassword(null);   // OTP-only account
	    user.setRole(Role.USER);
	    userservice.saveUser(user);

	    return ResponseEntity.ok(Map.of("message", "Account created successfully"));
	}

}
