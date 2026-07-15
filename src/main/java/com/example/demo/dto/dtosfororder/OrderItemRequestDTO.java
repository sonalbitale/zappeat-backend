package com.example.demo.dto.dtosfororder;

public class OrderItemRequestDTO {
	private Long foodItemId;
	private Integer quantity;
	
	public OrderItemRequestDTO() {
		
		
	}

	public Long getFoodItemId() {
		return foodItemId;
	}

	public void setFoodItemId(Long foodItemId) {
		this.foodItemId = foodItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quanity) {
		this.quantity = quanity;
	}
	

}
