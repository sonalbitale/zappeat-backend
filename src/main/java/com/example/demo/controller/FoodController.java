package com.example.demo.controller;

import com.example.demo.repo.FoodItemRepositoryy;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FoodItemDTO;
import com.example.demo.entity.FoodItem;
import com.example.demo.jwtutl.JwtUtil;
import com.example.demo.service.FoodService;

@RestController
@RequestMapping("/foods")
public class FoodController {
	
	private final FoodItemRepositoryy foodItemRepositoryy;
	@Autowired
	private FoodService foodservice;
	
	@Autowired
	private JwtUtil jwt;

	FoodController(FoodItemRepositoryy foodItemRepositoryy) {
		this.foodItemRepositoryy = foodItemRepositoryy;
	}
	
	
	
	
	
	// Public — customer-facing menu, all approved restaurants' food
	@GetMapping("/getallcustomer-facingmenu")
	public ResponseEntity<List<FoodItem>> getAllFood() {
	    return ResponseEntity.ok(foodservice.getAllFoodItems());
	 
	}
	@GetMapping("/{id}")
	public ResponseEntity<FoodItem> getFoodById(@PathVariable Long id) {
		        FoodItem food = foodservice.getFoodById(id); 
				
				return ResponseEntity.ok(food);
	}
	
//	@PostMapping("/addfood")
//	public ResponseEntity<FoodItem> addFood(@RequestBody FoodItemDTO  dto) {
//		FoodItem food= new  FoodItem();
//		
//		food.setName(dto.getName());
//		food.setCategory(dto.getCategory());
//		food.setImageUrl(dto.getImageURL());
//		food.setPrice(dto.getPrice());
//		FoodItem savefood = foodservice.addFood(food);
//		 
//		
//		 return ResponseEntity.status(HttpStatus.CREATED).body(savefood);
//	}
//	
	
	
	
	
	@PostMapping("/vendor/food-items")
	@PreAuthorize("hasRole('VENDOR')")
	public ResponseEntity<?> addFoodItem(Authentication auth, @RequestBody FoodItemDTO dto) {
	    String username = auth.getName();
	    FoodItem saved = foodservice.addFoodItem(username, dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	
	
//	get food items of specific vendor
	
	@GetMapping("/vendor/getfood-items")
	@PreAuthorize("hasRole('VENDOR')")
	public ResponseEntity<?> getMyFoodItems(Authentication auth) {
		System.out.println("hii");
	    String username = auth.getName();
	    
	    List<FoodItem> items = foodservice.getFoodItemsForVendor(username);
	    return ResponseEntity.ok(items);
	}
	
	
	
	
	
	
	@DeleteMapping("/vendor/deletefooditem/{id}")
	@PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<String> deleteById(Authentication auth,@PathVariable Long id) {
		   String username = auth.getName();
		foodservice.deleteFoodById(username,id);
		return  ResponseEntity.ok("food of the given id has been successfully deld");
	}
	
	
	@PutMapping("vendor/updatefooditem/{id}")
	@PreAuthorize("hasRole('VENDOR')")
    ResponseEntity<FoodItem> updateFoodById(Authentication auth,  @PathVariable Long id, @RequestBody FoodItemDTO food) {
		   String username = auth.getName();
	  FoodItem updateFood = foodservice.updateFood(username,id, food);
	  
	 return  ResponseEntity.ok(updateFood);
	  
	  
	  
	}
	
	@GetMapping("/vendor/getfooditems/{id}")
	@PreAuthorize("hasRole('VENDOR')")
	public ResponseEntity<?> getMyFoodItemById(Authentication auth, @PathVariable Long id) {
	    FoodItem item = foodservice.getFoodItemForVendorById(auth.getName(), id);
	    return ResponseEntity.ok(item);
	}
	

}
