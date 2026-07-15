package com.example.demo.entity.vendor;

import jakarta.persistence.Entity;

import com.example.demo.entity.User;

import jakarta.persistence.*;

	@Entity
	@Table(name = "restaurant")
	public class Restaurant {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String restaurantName;
	    private String fssaiLicense;
	    private String businessAddress;
	    private String contactPhone;
	    private boolean isApproved = false;   // admin must approve before going live

	    @OneToOne
	    @JoinColumn(name = "owner_user_id")
	    private User owner;

	    public Restaurant() {}

	    public Long getId() { return id; }

	    public String getRestaurantName() { return restaurantName; }
	    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

	    public String getFssaiLicense() { return fssaiLicense; }
	    public void setFssaiLicense(String fssaiLicense) { this.fssaiLicense = fssaiLicense; }

	    public String getBusinessAddress() { return businessAddress; }
	    public void setBusinessAddress(String businessAddress) { this.businessAddress = businessAddress; }

	    public String getContactPhone() { return contactPhone; }
	    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

	    public boolean isApproved() { return isApproved; }
	    public void setApproved(boolean approved) { this.isApproved = approved; }

	    public User getOwner() { return owner; }
	    public void setOwner(User owner) { this.owner = owner; }
	

}
