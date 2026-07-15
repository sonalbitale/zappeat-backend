package com.example.demo.service;
import java.util.List;

import com.example.demo.dto.FoodItemDTO;
import com.example.demo.entity.FoodItem;


public interface FoodService {
	
	 List<FoodItem> getAllFoodItems();
	FoodItem getFoodById(Long id);
	
	FoodItem addFoodItem(String user, FoodItemDTO food);
	
	void deleteFoodById(String username, Long id);
	
	FoodItem updateFood(String User, Long id, FoodItemDTO food);
	
//	get specific vendor food items 
	List<FoodItem> getFoodItemsForVendor(String username);
	
//	getfood item by id to load the fooditem for updating
	FoodItem getFoodItemForVendorById(String username, Long id);
	
	

}
