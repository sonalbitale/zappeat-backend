package com.example.demo.entity;

import java.time.LocalDateTime;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.AnyDiscriminatorImplicitValues.Strategy;

import com.example.demo.embeddedclass.Address;
import com.example.demo.entity.vendor.Restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;


@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Order_Items>  orderitems;
	
	private double totalprice;
	
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderstatus;
	
	private LocalDateTime orderdate;
	private LocalDateTime assignedAt;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	public  Order() {
		this.orderdate= LocalDateTime.now();
		this.orderstatus=OrderStatus.PLACED;
		
		
	}
	
	
	  @Embedded
	private Address deliveryAddress;

	private String paymentMethod;
	
	private String rejectionReason;
	private Long deliveryBoyId; // null initially

	
	
//	getter and setter

	

	public User getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public List<Order_Items> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<Order_Items> orderitem) {
		this.orderitems = orderitem;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public OrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}

	public LocalDateTime getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(LocalDateTime orderdate) {
		this.orderdate = orderdate;
	}
	
	
	
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Long getDeliveryBoyId() {
		return deliveryBoyId;
	}

	public void setDeliveryBoyId(Long deliveryBoyId) {
		this.deliveryBoyId = deliveryBoyId;
	}

	public LocalDateTime getAssignedAt() {
		return assignedAt;
	}

	public void setAssignedAt(LocalDateTime assignedAt) {
		this.assignedAt = assignedAt;
	}
	
	

}
