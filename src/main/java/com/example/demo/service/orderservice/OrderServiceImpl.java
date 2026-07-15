package com.example.demo.service.orderservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.dtosfororder.AddressDTO;
import com.example.demo.dto.dtosfororder.OrderItemRequestDTO;
import com.example.demo.dto.dtosfororder.OrderItemResponseDTO;
import com.example.demo.dto.dtosfororder.PlaceOrderRequestDTO;
import com.example.demo.dto.dtosfororder.placedOrderResponseDTO;
import com.example.demo.dto.dtosfororder.deliverymoduledto.OrderNotification;
import com.example.demo.embeddedclass.Address;
import com.example.demo.entity.FoodItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Order_Items;
import com.example.demo.entity.User;
import com.example.demo.entity.vendor.Restaurant;
import com.example.demo.repo.DeliveryBoyRepository;
import com.example.demo.repo.FoodItemRepositoryy;
import com.example.demo.repo.OrderRepository;
import com.example.demo.repo.RestaurantRepository;
import com.example.demo.repo.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class OrderServiceImpl implements OrderService{
	
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private FoodItemRepositoryy foodrepo;
	
	@Autowired
	private OrderRepository orderrepo;
	
	@Autowired
	private RestaurantRepository restaurantrepo;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private DeliveryBoyRepository deliveryrepo;

	
	
	
	
	

	@Override
	@Transactional
	public placedOrderResponseDTO placeOrderrequest(String username, PlaceOrderRequestDTO placeorderrequestdto) {
 
		
//		step 1 find user for having user check as well to pass that user to order object which will stand this users order
		
              Optional<User> dbuser = 	 Optional.of(userrepo.findByUsername(username).orElseThrow(()-> new RuntimeException("user not found")));
      		  User user = dbuser.get();
//      set user into oder to tell its user sonal,s order     		  
      		  Order order = new Order();
      		  order.setUser(user);
      		  
      		  
      		  // ✅ 3. Set  payment
//      	    order.setDeliveryAddress(placeorderrequestdto.getDeliveryAddress());
      	    order.setPaymentMethod(placeorderrequestdto.getPaymentMethod());
      		  
//  step 3 empty list
      		  
      		  List<Order_Items> orderitems= new ArrayList<>();
      		  Double totalprice = 0.0;
      		 Restaurant orderRestaurant = null;
// step 4 loop though every items 
      		  
      		  for( OrderItemRequestDTO orderitemsfromclientside : placeorderrequestdto.getItems()) {
      			  
      			  FoodItem food =  foodrepo.findById(orderitemsfromclientside.getFoodItemId()).orElseThrow(()-> new RuntimeException("food not found"));
      			  
      			  // 🔒 derive + enforce single restaurant per order
      		    if (orderRestaurant == null) {
      		        orderRestaurant = food.getRestaurant();
      		    } else if (!orderRestaurant.getId().equals(food.getRestaurant().getId())) {
      		        throw new RuntimeException("Order contains items from multiple restaurants. Please order separately.");
      		    }
      		    
      		    
      			  
      			  Order_Items orderitem= new Order_Items();
      			  
      			  orderitem.setOrder(order);
      			  orderitem.setFooditem(food);
      			  orderitem.setPrice(food.getPrice()*orderitemsfromclientside.getQuantity());
      			  orderitem.setQuantity(orderitemsfromclientside.getQuantity());
      			  
      			  
      			  orderitems.add(orderitem);
      			  totalprice+=orderitem.getPrice();
      			  
      			  
      			  
      		  }
      		  
//      		  addressdto to address class mapping
      		  
      		AddressDTO dtoAddress = placeorderrequestdto.getDeliveryAddress();

      		Address address = new Address();
      		address.setPhone(dtoAddress.getPhone());
      		address.setAddress(dtoAddress.getAddress());
      		address.setCity(dtoAddress.getCity());
      		address.setPincode(dtoAddress.getPincode());
      		address.setLandmark(dtoAddress.getLandmark());
      		address.setType(dtoAddress.getType());
      		

      		    
      		     
      		 
      		  
      		  order.setOrderitems(orderitems);
      		  order.setOrderstatus(OrderStatus.PLACED);
      		  order.setTotalprice(totalprice);
        	  order.setDeliveryAddress(address);
        	  order.setRestaurant(orderRestaurant);
 
      		  
      		  
      		  
      		  
      		 
//      		  save  
      		orderrepo.save(order);
      		
      		  
      		  
      		  

		
		return convertToResponseDTO(order); 
	}
	
	
	

	@Override
	public List<placedOrderResponseDTO> getMyOrders(String username) {
		
	 User user =	userrepo.findByUsername(username).orElseThrow(()-> new RuntimeException("user not found"));
	 List<Order> orders =  (List<Order>) orderrepo.findById(user.getId()).orElseThrow(()-> new RuntimeException("user not found"));
	 
	 List<placedOrderResponseDTO>   response= new ArrayList<>();
	 
	 for(Order order :orders) {
		 response.add(convertToResponseDTO(order));
		 
	 }
	        
		
		return response;
	}

	@Override
	public placedOrderResponseDTO getOrderByID(Long id) {
		
		 Order order  = orderrepo.findById(id).orElseThrow(()-> new RuntimeException("not found"));
		// TODO Auto-generated method stub
		return convertToResponseDTO(order);
	}
	
	
	 placedOrderResponseDTO convertToResponseDTO(Order order) {
		 
		 placedOrderResponseDTO placedorderresponse = new placedOrderResponseDTO();
		 
		 placedorderresponse.setOrderid(order.getId());
		 placedorderresponse.setDate(order.getOrderdate());
		 
		 placedorderresponse.setStatus(order.getOrderstatus().name());
		 placedorderresponse.setTotalprice(order.getTotalprice());
		 
		 
		 
		 
		 List<OrderItemResponseDTO>  placedorderitem = new ArrayList<>();
		 
		 for(Order_Items orderitems :order.getOrderitems()) {
			 
			 OrderItemResponseDTO orderitemresponsedto = new OrderItemResponseDTO();
			 
			 orderitemresponsedto.setFoodName(orderitems.getFooditem().getName());
			 
			 orderitemresponsedto.setPrice(orderitems.getPrice());
			 orderitemresponsedto.setQuanity(orderitems.getQuantity());
			 
			 
			 placedorderitem.add(orderitemresponsedto);
			// in convertToResponseDTO
			 placedorderresponse.setRestaurantName(order.getRestaurant().getRestaurantName());
			 
			 
			 
			
			 
		 }
		 
		 placedorderresponse.setItems(placedorderitem);
 		 
		Address address = order.getDeliveryAddress();
		
		AddressDTO addressdto = new AddressDTO();

		addressdto.setPhone(address.getPhone());
		addressdto.setAddress(address.getAddress());
		addressdto.setCity(address.getCity());
		addressdto.setPincode(address.getPincode());
		addressdto.setLandmark(address.getLandmark());
		addressdto.setType(address.getType());
		
		placedorderresponse.setDeliveryaddress(addressdto);// in convertToResponseDTO
		placedorderresponse.setRestaurantName(order.getRestaurant().getRestaurantName());
		
		 
		 
		return placedorderresponse;

	}
	 
//	  get orders for vendor specific to show on dashboard
	 
	 @Override
	 public List<Order> getOrdersForVendor(String username) {
	     User vendorUser = userrepo.findByUsername(username)
	             .orElseThrow(() -> new RuntimeException("user not found"));

	     Restaurant restaurant = restaurantrepo.findByOwner(vendorUser)
	             .orElseThrow(() -> new RuntimeException("no restaurant profile for this vendor"));

	     return orderrepo.findByRestaurantOrderByOrderdateDesc(restaurant);
	 }
	 
	 
	 
//	 acccpet reject  orders by vendor
	 
	 @Override
	 @Transactional
	 public Order acceptOrder(String username, Long orderId) {
	     Order order = getOwnedOrder(username, orderId);

	     if (order.getOrderstatus() != OrderStatus.PLACED) {
	         throw new RuntimeException("Order cannot be accepted from its current status: " + order.getOrderstatus());
	     }

	     order.setOrderstatus(OrderStatus.PREPARING);
	     return orderrepo.save(order);
	 }

	 @Override
	 @Transactional
	 public Order rejectOrder(String username, Long orderId, String reason) {
	     Order order = getOwnedOrder(username, orderId);

	     if (order.getOrderstatus() != OrderStatus.PLACED) {
	         throw new RuntimeException("Order cannot be rejected from its current status: " + order.getOrderstatus());
	     }

	     order.setOrderstatus(OrderStatus.REJECTED);
	     order.setRejectionReason(reason);
	     return orderrepo.save(order);
	 }

	 // shared ownership check, reused by accept/reject/ready
	 private Order getOwnedOrder(String username, Long orderId) {
	     User vendorUser = userrepo.findByUsername(username)
	             .orElseThrow(() -> new RuntimeException("user not found"));

	     Order order = orderrepo.findById(orderId)
	          .orElseThrow(() -> new RuntimeException("order not found"));
	     
	     Long vendorOrderId=  order.getRestaurant().getOwner().getId();
	     

	     if (!vendorOrderId.equals(vendorUser.getId())) {
	         throw new RuntimeException("you do not own this order");
	     }

	     return order;
	 }
	 

	 
	 @Override
	 @Transactional
	 public Order markReady(String username, Long orderId) {
	     Order order = getOwnedOrder(username, orderId);

	     if (order.getOrderstatus() != OrderStatus.PREPARING ) {
	         throw new RuntimeException("Order must be in PREPARING status to mark as ready");
	     }

	     order.setOrderstatus(OrderStatus.READY_FOR_PICKUP);

	     Order savedOrder = orderrepo.save(order);

	     OrderNotification notification = new OrderNotification(
	         savedOrder.getId(),
	         savedOrder.getRestaurant().getRestaurantName(),
	         savedOrder.getDeliveryAddress().getAddress()
	     );

	     messagingTemplate.convertAndSend("/topic/orders", notification);

	     return savedOrder;
	 }
	 
	 @Override
	 @Transactional
	 public Order acceptOrderByDeliveryBoy(String username, Long orderId) {

	     User user = userrepo.findByUsername(username)
	         .orElseThrow(() -> new RuntimeException("User not found"));

	     Order order = orderrepo.findById(orderId)
	         .orElseThrow(() -> new RuntimeException("Order not found"));

	     // 🔥 SAFETY CHECK
	     if (order.getOrderstatus() != OrderStatus.READY_FOR_PICKUP 
	         || order.getDeliveryBoyId() != null) {

	         throw new RuntimeException("Order already taken");
	     }

	     // ✅ Assign
	     order.setDeliveryBoyId(user.getId());
	     order.setOrderstatus(OrderStatus.ASSIGNED);
	     order.setAssignedAt(LocalDateTime.now());

	     Order savedOrder = orderrepo.save(order);

	     // 🔥 WebSocket broadcast
	     messagingTemplate.convertAndSend(
	         "/topic/order-assigned", orderId
	     );

	     return savedOrder;
	 }
	 
//	 getall orders by delivery boy whose status is ready for pickup
	 
	 @Override
	public List<Order> getAllOrders(){
		List<Order> order= orderrepo.findAll();
		
		List<Order> orderNeedDelivery = new ArrayList<>();
		for(Order savedorder: order) {
			if(savedorder.getOrderstatus() == OrderStatus.READY_FOR_PICKUP) {
			    orderNeedDelivery.add(savedorder);
			}
			
			
			
		}
		return orderNeedDelivery;
		 }
	 
	 
	 
	 @Override
	 @Transactional
	 public Order markOutForDelivery(String username, Long orderId) {
	     User user = userrepo.findByUsername(username)
	         .orElseThrow(() -> new RuntimeException("User not found"));

	     Order order = orderrepo.findById(orderId)
	         .orElseThrow(() -> new RuntimeException("Order not found"));

	     if (order.getOrderstatus() != OrderStatus.ASSIGNED
	         || !order.getDeliveryBoyId().equals(user.getId())) {
	         throw new RuntimeException("Order cannot be picked up");
	     }

	     order.setOrderstatus(OrderStatus.OUT_FOR_DELIVERY);
	     Order savedOrder = orderrepo.save(order);

	     messagingTemplate.convertAndSend("/topic/order-status/" + orderId, "OUT_FOR_DELIVERY");
	     return savedOrder;
	 }

	 @Override
	 @Transactional
	 public Order markDelivered(String username, Long orderId) {
	     User user = userrepo.findByUsername(username)
	         .orElseThrow(() -> new RuntimeException("User not found"));

	     Order order = orderrepo.findById(orderId)
	         .orElseThrow(() -> new RuntimeException("Order not found"));

	     if (order.getOrderstatus() != OrderStatus.OUT_FOR_DELIVERY
	         || !order.getDeliveryBoyId().equals(user.getId())) {
	         throw new RuntimeException("Order cannot be marked delivered");
	     }

	     order.setOrderstatus(OrderStatus.DELIVERED);
	     Order savedOrder = orderrepo.save(order);

	     messagingTemplate.convertAndSend("/topic/order-status/" + orderId, "DELIVERED");
	     return savedOrder;
	 }
	 
	 
	 @Override
	 public List<Order> getOrdersForDeliveryBoy(String username)  {
	     User user = userrepo.findByUsername(username).orElseThrow();
	     return orderrepo.findByDeliveryBoyIdAndOrderstatusIn(user.getId(), List.of(OrderStatus.ASSIGNED, OrderStatus.OUT_FOR_DELIVERY)
	     );
	 }
}
