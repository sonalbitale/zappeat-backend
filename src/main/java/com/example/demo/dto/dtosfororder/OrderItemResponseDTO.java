package com.example.demo.dto.dtosfororder;

public class OrderItemResponseDTO {

	private String foodName;
    private Integer quanity;
    private double price;
    
    public OrderItemResponseDTO(){
    	
    }

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Integer getQuanity() {
		return quanity;
	}

	public void setQuanity(Integer quanity) {
		this.quanity = quanity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
    
    
}
