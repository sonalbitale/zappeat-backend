package com.example.demo.dto;

public class FoodItemDTO {

	private String name ="";
	private double price ;
	private String category;
	private String imageUrl;
	
	public  FoodItemDTO(){
		
	}
	
	public String getName() {
		return name;
		
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getImageURL() {
		return imageUrl;
	}
	
	public Double getPrice(){
	return price;
	}
	
	public void  setName(String name) {
		this.name=name;
	}
	
	public void setPrice(Double price) {
	        this.price = price;
	  }
	
	
	public void setCategory(String category) {
        this.category = category;
    }
	
	 public void setImageUrl(String imageUrl) {
	        this.imageUrl = imageUrl;
	    }

}
