package com.example.demo.entity;

import com.example.demo.entity.vendor.Restaurant;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "food_item")
public class FoodItem {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private Double price;
	    private String category;
	    private String imageUrl;
	    @ManyToOne
	    @JoinColumn(name = "restaurant_id")
	    private Restaurant restaurant;
	 
	   
	    
	    public FoodItem(){
	    	
	    }
	    
	    public FoodItem(Long id, String name, Double price, String category, String imageUrl, Restaurant restaurant) {
	    	this.id=id;
	    	this.name=name;
	    	this.price=price;
	    	this.category=category;
	    	this.imageUrl=imageUrl;
	    	this.restaurant=restaurant;

	    	}
	    
	    
	    
	    // getter setter
	    public long getId() {
	    	return id;
	    	
	    }
	    
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Double getPrice() {
	        return price;
	    }

	    public void setPrice(Double price) {
	        this.price = price;
	    }

	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

	    public String getImageUrl() {
	        return imageUrl;
	    }

	    public void setImageUrl(String imageUrl) {
	        this.imageUrl = imageUrl;
	    }
	    public Restaurant getRestaurant() { return restaurant; }
	    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
	

}
