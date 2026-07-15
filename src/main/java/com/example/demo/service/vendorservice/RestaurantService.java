

package com.example.demo.service.vendorservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.vendor.Restaurant;
import com.example.demo.repo.RestaurantRepository;
import com.example.demo.repo.UserRepository;

@Service
public class RestaurantService implements RestaurantServiceInterface{
	
	
	
	@Autowired
	private UserRepository userrepo;
	@Autowired
    private RestaurantRepository restaurantrepo;
	
	
	@Override
	public Restaurant getRestaurantByOwner(String username) {
	    User user = userrepo.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("user not found"));

	    return restaurantrepo.findByOwner(user)
	            .orElseThrow(() -> new RuntimeException("no restaurant found for this vendor"));
	}
	
	

}
