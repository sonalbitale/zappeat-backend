package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JwtAuthFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final CorsConfigurationSource corsConfigurationSource;
	@Autowired
	private JwtAuthFilter jwtauthfilter;

	WebSecurityConfig(CorsConfigurationSource corsConfigurationSource) {
		this.corsConfigurationSource = corsConfigurationSource;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		
		  .csrf(csrf -> csrf.disable())
		 
		   
		.httpBasic(httpBasic->httpBasic.disable() )
		.formLogin(formLogin-> formLogin.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.authorizeHttpRequests(auth -> auth

//		      .requestMatchers(HttpMethod.GET, "/foods/**").hasAnyRole("ADMIN", "USER")
//		      .requestMatchers(HttpMethod.POST, "/foods/addfood**").hasAnyRole("ADMIN","USER")
		      
			   .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ IMPORTANT

		       // public signup api
		      .requestMatchers("/Api/signup").permitAll()
		      .requestMatchers("/Api/vendor-signup").permitAll()
		      .requestMatchers("/delivery-signup").permitAll()

		      

		      
		  
               // public login api		
		      .requestMatchers("/Api/login").permitAll()
		      
//		       if not logged in checkuser while checkout 
       	      .requestMatchers(HttpMethod.POST, "/Api/check-user").permitAll()
              .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ VERY IMPORTANT	
              .requestMatchers("/Api/send-otp").permitAll()
		      .requestMatchers("/Api/verify-otp").permitAll()
		      .requestMatchers(HttpMethod.POST, "/Api/register-guest").permitAll()
		      // User
		      .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
		      .requestMatchers(HttpMethod.GET, "/foods/getallcustomer-facingmenu").permitAll()      // ✅ anyone can browse menu
              //.requestMatchers("/api/foods/**").hasAnyRole("USER", "ADMIN", "VENDOR")  // writes still protected     //	      .requestMatchers("/orders/**").hasAnyRole("USER", "ADMIN")
		      .requestMatchers("/api/cart/**").hasAnyRole("USER", "ADMIN")

		      // Admin
		      .requestMatchers("/api/admin/**").hasRole("ADMIN")
		      
//		       for delivery boy
		      .requestMatchers(HttpMethod.PATCH, "/delivery/**").hasRole("DELIVERY_BOY")
		      .requestMatchers("/delivery/**").hasRole("DELIVERY_BOY")
		     
//         just for testing purpose
		      .requestMatchers("/ws/**").permitAll()
		       
		      .anyRequest().authenticated()
		  )
		  .addFilterBefore(jwtauthfilter, UsernamePasswordAuthenticationFilter.class) ;// registering auth filter  IMPORTANT
		 
		
		
		
		return http.build();
		
		
		
	}
	
	
    private CorsConfigurationSource corsConfigurationSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
  
	
	
	

}
