package com.example.demo.dto.dtosfororder.deliverymoduledto;

import com.example.demo.embeddedclass.Address;

public class OrderNotification {

	   private Long orderId;
	   private String restaurantName;
	   private String address;
	   
	   public OrderNotification(Long id, String restaurantName, String address) {
		   this.orderId=id;
		   this.restaurantName=restaurantName;
		   this.address=address;
		   
		   
		   
	   }
	   public Long getOrderId() {
		   return orderId;
	   }
	   public void setOrderId(Long orderId) {
		   this.orderId = orderId;
	   }
	   public String getRestaurantName() {
		   return restaurantName;
	   }
	   public void setRestaurantName(String restaurantName) {
		   this.restaurantName = restaurantName;
	   }
	   public String getAddress() {
		   return address;
	   }
	   public void setAddress(String address) {
		   this.address = address;
	   }

	   
	}

