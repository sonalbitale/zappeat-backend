package com.example.demo.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.dtosfororder.PlaceOrderRequestDTO;
import com.example.demo.dto.dtosfororder.RejectOrderDTO;
import com.example.demo.dto.dtosfororder.placedOrderResponseDTO;
import com.example.demo.entity.Order;
import com.example.demo.repo.OrderRepository;
import com.example.demo.service.orderservice.OrderServiceImpl;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderServiceImpl orderservice;
	
	
	@PostMapping()
	@PreAuthorize("hasRole('USER')")
	ResponseEntity<placedOrderResponseDTO> placeorder( Authentication auth ,  @RequestBody PlaceOrderRequestDTO placeorderrequestdto) {
		
	 String name = 	auth.getName();
     placedOrderResponseDTO  placedorderresponse = orderservice.placeOrderrequest(name, placeorderrequestdto);
	 
	 
		return ResponseEntity.status(HttpStatus.CREATED).body(placedorderresponse);
		
	}
	
	@GetMapping("/my")
	 ResponseEntity<List<placedOrderResponseDTO>> getallmyoders(Authentication auth){
		 
	String name= 	 auth.getName();
	 List<placedOrderResponseDTO>  placedorderresponse = orderservice.getMyOrders(name);
		 return ResponseEntity.ok(placedorderresponse);
		
	}
	
	@GetMapping("/{id}")
	
	ResponseEntity<placedOrderResponseDTO> getOrderByID(@PathVariable Long id){
		
		placedOrderResponseDTO  placedorderresponse =	orderservice.getOrderByID(id);
		
		return ResponseEntity.ok(placedorderresponse);
		
		
	}
	
	
	
	
//	get orders for vendor specific
	@GetMapping("/vendor/orders")
	@PreAuthorize("hasRole('VENDOR')")
	public ResponseEntity<?> getMyOrders(Authentication auth) {
	    String username = auth.getName();
	    List<Order> orders = orderservice.getOrdersForVendor(username);
	    return ResponseEntity.ok(orders);
	}
	
	
	
//	accept reject order by vendor
	@PatchMapping("/vendor/orders/{id}/accept")
	@PreAuthorize("hasRole('VENDOR')")
	public ResponseEntity<?> acceptOrder(Authentication auth, @PathVariable Long id) {
	    Order updated = orderservice.acceptOrder(auth.getName(), id);
	    return ResponseEntity.ok(updated);
	}

	@PatchMapping("/vendor/orders/{id}/reject")
	@PreAuthorize("hasRole('VENDOR')")
	public ResponseEntity<?> rejectOrder(Authentication auth, @PathVariable Long id, @RequestBody RejectOrderDTO dto) {
	    Order updated = orderservice.rejectOrder(auth.getName(), id, dto.getReason());
	    return ResponseEntity.ok(updated);
	}
	
	
//	when order is ready mark it as ready from vendor side and it will callthis api when update mart ready status 
	@PatchMapping("/vendor/orders/{id}/mark-ready")
	@PreAuthorize("hasRole('VENDOR')") 
	public ResponseEntity<?> markOrderReady(Authentication auth, @PathVariable Long id) {
	    Order updated = orderservice.markReady(auth.getName(), id);
	    return ResponseEntity.ok("Order is now waiting for delivery partner");
	}
	
	@GetMapping("/getallorders")
	@PreAuthorize("hasRole('DELIVERY_BOY')")
	List<Order> getAllOrders(){
		List<Order> order = orderservice.getAllOrders();
		
		
		return order;
		
	}
	

}
