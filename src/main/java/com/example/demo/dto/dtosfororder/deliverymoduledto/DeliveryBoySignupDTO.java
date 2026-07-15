package com.example.demo.dto.dtosfororder.deliverymoduledto;

	
	
	public class DeliveryBoySignupDTO {

	    private String username;
	    private String email;
	    private String password;
	    private String phone;

	    // vehicle details
	    private String vehicleType;   // BIKE / SCOOTER / CYCLE
	    private String vehicleNumber;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getVehicleType() {
			return vehicleType;
		}
		public void setVehicleType(String vehicleType) {
			this.vehicleType = vehicleType;
		}
		public String getVehicleNumber() {
			return vehicleNumber;
		}
		public void setVehicleNumber(String vehicleNumber) {
			this.vehicleNumber = vehicleNumber;
		}
	    
	    
	    
	    
	}


