package com.example.demo.controller.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DeliveryBoyProfile;
import com.example.demo.entity.vendor.Restaurant;
import com.example.demo.repo.DeliveryBoyRepository;
import com.example.demo.repo.RestaurantRepository;

@RestController
public class AdminController {
	
	
	
	@Autowired 
	private RestaurantRepository restaurantrepo;
	
	
	@Autowired
	private DeliveryBoyRepository deliveryRepo;
	
	@GetMapping("/admin/restaurants/pending")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getPendingRestaurants() {
	    List<Restaurant> pending = restaurantrepo.findByIsApprovedFalse();
	    return ResponseEntity.ok(pending);
	}
	
	
	
	@PatchMapping("/admin/restaurants/{id}/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> approveRestaurant(@PathVariable Long id) {
	    Restaurant restaurant = restaurantrepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("restaurant not found"));

	    restaurant.setApproved(true);
	    restaurantrepo.save(restaurant);

	    return ResponseEntity.ok("Restaurant approved successfully");
	}
	
	
	
	
	@GetMapping("/admin/delivery/pending")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getPendingDeliveryPartners() {
	    List<DeliveryBoyProfile> pending = deliveryRepo.findByApprovedFalse();
	    return ResponseEntity.ok(pending);
	}
	
	@PatchMapping("/admin/delivery/{id}/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> approveDelivery(@PathVariable Long id) {

		DeliveryBoyProfile dp = deliveryRepo.findById(id)
	        .orElseThrow(() -> new RuntimeException("Delivery partner not found"));

	    dp.setApproved(true);
	    deliveryRepo.save(dp);

	    return ResponseEntity.ok("Delivery partner approved successfully");
	}

}
