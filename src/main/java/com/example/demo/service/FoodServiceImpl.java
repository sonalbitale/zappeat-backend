package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FoodItemDTO;
import com.example.demo.entity.FoodItem;
import com.example.demo.entity.User;
import com.example.demo.entity.vendor.Restaurant;
import com.example.demo.repo.FoodItemRepositoryy;
import com.example.demo.repo.RestaurantRepository;
import com.example.demo.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodItemRepositoryy foodrepo;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private RestaurantRepository restaurantrepo;
	
//	@Override
//	public
//    List<FoodItem> getallFoodItems(){
//		return foodrepo.findAll();
//		
//	}
	
	
	


	
	@Override
    public FoodItem getFoodById(Long id) {
		return foodrepo.findById(id).orElse(null);
	}
	
	 @Override
	    @Transactional
	    public FoodItem addFoodItem(String username, FoodItemDTO dto) {

	        User vendorUser = userrepo.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("user not found"));

	        Restaurant restaurant = restaurantrepo.findByOwner(vendorUser)
	                .orElseThrow(() -> new RuntimeException("no restaurant profile for this vendor"));

	        if (!restaurant.isApproved()) {
	            throw new RuntimeException("restaurant not approved yet, cannot add items");
	        }

	        FoodItem food = new FoodItem();
	        food.setName(dto.getName());
	        food.setPrice(dto.getPrice());
	        food.setCategory(dto.getCategory());
	        food.setImageUrl(dto.getImageURL());
	        food.setRestaurant(restaurant);

	        return foodrepo.save(food);
	    }
	
//	@Override
//	public void deleteFoodById(Long id) {
//     foodrepo.deleteById(id);
//	}
    
//	@Override
//	public FoodItem updateFood(Long id , FoodItem fooditem) {
//		FoodItem fooditem1 = foodrepo.findById(id).orElse(null);
//		if(id!=null)
//			fooditem1.setName(fooditem.getName());
//		    fooditem1.setPrice(fooditem.getPrice());
//		
//		return fooditem1;
//		
//	}
	
	
	@Override
	public FoodItem updateFood(String user, Long id, FoodItemDTO fooddto) {
	    User vendorUser = userrepo.findByUsername(user)
	            .orElseThrow(() -> new RuntimeException("user not found"));

	    FoodItem food = foodrepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("food item not found"));
       
	    
	    Long ownerId =food.getRestaurant().getOwner().getId();
	    if(!ownerId.equals(vendorUser.getId()))
	     {
	        throw new RuntimeException("you do not own this food item");
	    }

	    food.setName(fooddto.getName());
	    food.setPrice(fooddto.getPrice());
	    food.setCategory(fooddto.getCategory());
	    food.setImageUrl(fooddto.getImageURL());

	    return foodrepo.save(food);  }

	
	
	
	
	
	 
	@Override
	public void deleteFoodById(String username, Long id) {
	    User vendorUser = userrepo.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("user not found"));

	    FoodItem food = foodrepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("food item not found"));
    Long ownerId=food.getRestaurant().getOwner().getId();
	    if (!ownerId.equals(vendorUser.getId())) {
	        throw new RuntimeException("you do not own this food item");
	    }

	    foodrepo.deleteById(id);
	}
	
	
	
	
	@Override
	public List<FoodItem> getAllFoodItems() {
	    return foodrepo.findAll().stream()
	            .filter(item -> item.getRestaurant() != null && item.getRestaurant().isApproved())
	            .toList();
	}

	@Override
	public List<FoodItem> getFoodItemsForVendor(String username) {
	    User vendorUser = userrepo.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("user not found"));

	    Restaurant restaurant = restaurantrepo.findByOwner(vendorUser)
	            .orElseThrow(() -> new RuntimeException("no restaurant profile for this vendor"));

	    return foodrepo.findByRestaurant(restaurant);
	}

	@Override
	public FoodItem getFoodItemForVendorById(String username, Long id) {
	    User vendorUser = userrepo.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("user not found"));

	    FoodItem food = foodrepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("food item not found"));
	    Long ownerId=food.getRestaurant().getOwner().getId();

	    if (!ownerId.equals(vendorUser.getId())) {
	        throw new RuntimeException("you do not own this food item");
	    }

	    return food;
	}
}
