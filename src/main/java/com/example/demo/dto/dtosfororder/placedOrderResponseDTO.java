package com.example.demo.dto.dtosfororder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.User;

public class placedOrderResponseDTO {
	private Long orderid;
	private String status;
	private double totalprice;
	private LocalDateTime date;
	private AddressDTO deliveryaddress;
    private String paymentMethod;
    
    private User deliveryBoy;
 // in placedOrderResponseDTO
    private String restaurantName;

	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	private List<OrderItemResponseDTO> items;
	public placedOrderResponseDTO() {
		
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public List<OrderItemResponseDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemResponseDTO> items) {
		this.items = items;
	}
	public AddressDTO getDeliveryaddress() {
		return deliveryaddress;
	}
	public void setDeliveryaddress(AddressDTO deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public User getDeliveryBoy() {
		return deliveryBoy;
	}
	public void setDeliveryBoy(User deliveryBoy) {
		this.deliveryBoy = deliveryBoy;
	}
	
	
	
	
	
	

}
