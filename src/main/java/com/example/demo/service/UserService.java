package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.dtosfororder.deliverymoduledto.DeliveryBoySignupDTO;
import com.example.demo.dto.vendorsignupdto.VendorSignupDTO;
import com.example.demo.entity.DeliveryBoyProfile;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.vendor.Restaurant;
import com.example.demo.repo.DeliveryBoyRepository;
import com.example.demo.repo.RestaurantRepository;
import com.example.demo.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userepo;
	
	@Autowired
	private  RestaurantRepository restaurantrepo;
	
	@Autowired
	private DeliveryBoyRepository deliveryRepo;

	UserService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public User saveUser(User user) {
		return userepo.save(user);
	}
	@Transactional
	public boolean existsByPhone(String phone) {
	    return userepo.existsByPhone(phone);
	}
	
	@Transactional
	public String vendorSignUp(VendorSignupDTO dto) {

	    User user = new User();
	    user.setUsername(dto.getUsername());
	    user.setEmailid(dto.getEmail());
	    user.setPassword(passwordEncoder.encode(dto.getPassword()));
	    user.setRole(Role.VENDOR);
	    userepo.save(user);

	    Restaurant restaurant = new Restaurant();
	    restaurant.setRestaurantName(dto.getRestaurantName());
	    restaurant.setFssaiLicense(dto.getFssaiLicense());
	    restaurant.setBusinessAddress(dto.getBusinessAddress());
	    restaurant.setContactPhone(dto.getContactPhone());
	    restaurant.setOwner(user);
	    restaurant.setApproved(false);
	    restaurantrepo.save(restaurant);

	    return "Vendor registered. Awaiting admin approval.";
	}
	
	
	@Transactional
	public String deliverySignup(DeliveryBoySignupDTO dto) {

	    // 1. Create User
	    User user = new User();
	    user.setUsername(dto.getUsername());
	    user.setEmailid(dto.getEmail());
	    user.setPassword(passwordEncoder.encode(dto.getPassword()));
	    user.setRole(Role.DELIVERY_BOY);
	    userepo.save(user);

	    // 2. Create Delivery Profile
	    DeliveryBoyProfile profile = new DeliveryBoyProfile();
	    profile.setVehicleType(dto.getVehicleType());
	    profile.setVehicleNumber(dto.getVehicleNumber());
	    profile.setUser(user);
	    profile.setApproved(false);
	    profile.setAvailable(false);

	    deliveryRepo.save(profile);

	    return "Delivery Boy registered. Awaiting admin approval.";
	}

	
	public User findOrCreateByPhone(String phone) {
	    return userepo.findByPhone(phone)
	        .orElseGet(() -> {
	            User newUser = new User();
	            newUser.setPhone(phone);
	            newUser.setUsername("user_" + phone);   // placeholder username since none was given
	            newUser.setPassword(null);               // OTP-only user, no password
	            newUser.setRole(Role.USER);
	            return userepo.save(newUser);
	        });
	}
}
