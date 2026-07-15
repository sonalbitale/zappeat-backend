package com.example.demo.service.orderservice;

import com.example.demo.dto.dtosfororder.PlaceOrderRequestDTO;
import com.example.demo.dto.dtosfororder.placedOrderResponseDTO;
import com.example.demo.entity.Order;

import java.util.*;

public interface OrderService {

	
 // to place the order	
	placedOrderResponseDTO  placeOrderrequest(String username, PlaceOrderRequestDTO placeorderrequestdto );
	
//	to get my orders
	
	List<placedOrderResponseDTO>  getMyOrders(String username);
	
	
//	get orderbyid
	
	placedOrderResponseDTO	 getOrderByID(Long id);
	
//	get order for vendor specific 
	
	List<Order> getOrdersForVendor(String username);
	
//	accept and reject order for vendor
	Order acceptOrder(String username, Long orderId);
	Order rejectOrder(String username, Long orderId, String reason);
	Order markReady(String username, Long orderId);
	
//	 delivery boy get orders in his dahsboard whose status is ready for pickup the order
	
	
	List<Order>	 getAllOrders();
	
//	 delivery boy accept orders 
  Order 	acceptOrderByDeliveryBoy(String username,Long  orderId);
  
//  delivery boy pickup orders and wan t o update status to out for delivery
  Order markOutForDelivery(String username, Long orderId);
  
//delivery boy pickup orders and wan t o update status to out for delivered
  Order markDelivered(String username, Long orderId);
  
  List<Order> getOrdersForDeliveryBoy(String username) ;
  
 
  
	

}
