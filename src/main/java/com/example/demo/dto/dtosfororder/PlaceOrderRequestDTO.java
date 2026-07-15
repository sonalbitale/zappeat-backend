package com.example.demo.dto.dtosfororder;

import java.util.List;

import com.example.demo.embeddedclass.Address;

public class PlaceOrderRequestDTO {
	
	 private List<OrderItemRequestDTO> items;
	 
	 private AddressDTO deliveryAddress;
    
	    private String paymentMethod;
	 public PlaceOrderRequestDTO() {
		 
	 }

	 public List<OrderItemRequestDTO> getItems() {
		 return items;
	 }

	 public void setItems(List<OrderItemRequestDTO> items) {
		 this.items = items;
	 }

	 public AddressDTO getDeliveryAddress() {
		 return deliveryAddress;
	 }

	 public void setDeliveryAddress(AddressDTO deliveryAddress) {
		 this.deliveryAddress = deliveryAddress;
	 }

	 public String getPaymentMethod() {
		 return paymentMethod;
	 }

	 public void setPaymentMethod(String paymentMethod) {
		 this.paymentMethod = paymentMethod;
	 }
	 
	 

}
