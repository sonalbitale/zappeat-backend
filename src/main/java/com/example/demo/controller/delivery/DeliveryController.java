package com.example.demo.controller.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.dtosfororder.deliverymoduledto.DeliveryBoySignupDTO;
import com.example.demo.dto.dtosfororder.deliverymoduledto.TrackingDto;
import com.example.demo.entity.Order;
import com.example.demo.service.UserService;
import com.example.demo.service.orderservice.OrderService;

@RestController
public class DeliveryController {
	
	
	@Autowired
	private OrderService orderservice;
	
	@Autowired
	private  UserService userservice;
	

@Autowired
private SimpMessagingTemplate messagingTemplate;
   

    @PostMapping("/delivery-signup")
    public ResponseEntity<?> deliverySignup(@RequestBody DeliveryBoySignupDTO dto) {
    String message = userservice.deliverySignup(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(message);
}


	
	@PatchMapping("/delivery/orders/{id}/accept")
	@PreAuthorize("hasRole('DELIVERY_BOY')")
	@ResponseBody
	public Order acceptOrder(Authentication auth, @PathVariable Long id) {
    System.out.println("role of delivery"+auth.getAuthorities());
		String name= 	 auth.getName();
	    return orderservice.acceptOrderByDeliveryBoy(name, id);
	}
	
	
//	(WebSocket for Tracking) deliveryboy will send location from is side 
	@MessageMapping("/tracking")
	public void updateLocation(TrackingDto tracking) {

	    // tracking contains:
	    // orderId, lat, lng

	    messagingTemplate.convertAndSend(
	        "/topic/tracking/" + tracking.getOrderId(),
	        tracking
	    );
	}
	
	
	
	
//	delivery boy when want to update the status to outfordelivery after picking up the order
	@PatchMapping("/delivery/orders/{id}/pickup")
	@PreAuthorize("hasRole('DELIVERY_BOY')")
	public Order pickupOrder(Authentication auth, @PathVariable Long id) {
	    return orderservice.markOutForDelivery(auth.getName(), id);
	}
	
//	//	delivery boy when want to update the status to delivered after delivered  the order


	@PatchMapping("/delivery/orders/{id}/delivered")
	@PreAuthorize("hasRole('DELIVERY_BOY')")
	public Order deliverOrder(Authentication auth, @PathVariable Long id) {
	    return orderservice.markDelivered(auth.getName(), id);
	}
	
//	endpoint for "my active orders" the ones this delivery boy has accepted, so they show up after waitingOrders disappears from the general pool
	
	@GetMapping("/delivery/orders/my")
	@PreAuthorize("hasRole('DELIVERY_BOY')")
	public List<Order> getMyOrders(Authentication auth) {
	    return orderservice.getOrdersForDeliveryBoy(auth.getName());
	}
}
