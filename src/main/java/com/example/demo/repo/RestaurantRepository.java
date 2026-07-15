package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;
import com.example.demo.entity.vendor.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	 Optional<Restaurant> findByOwner(User owner);
	 List<Restaurant> findByIsApprovedFalse();
}
