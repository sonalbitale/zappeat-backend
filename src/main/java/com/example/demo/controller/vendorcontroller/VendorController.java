
package com.example.demo.controller.vendorcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.vendor.Restaurant;
import com.example.demo.service.vendorservice.RestaurantService;

@RestController
public class VendorController {
	
	
	@Autowired
	private RestaurantService  restaurantService;
	@GetMapping("/vendor/my-restaurant")
	@PreAuthorize("hasRole('VENDOR')")
	public ResponseEntity<?> getMyRestaurant(Authentication auth) {
	    String username = auth.getName();
	    Restaurant restaurant = restaurantService.getRestaurantByOwner(username);
	    return ResponseEntity.ok(restaurant);
	}

}
