package com.example.demo.dto.dtosfororder.deliverymoduledto;

public class TrackingDto {
    private Long orderId;
    private double lat;
    private double lng;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
    
    
    
}