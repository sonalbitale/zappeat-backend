package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DeliveryBoyProfile;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoyProfile, Long> {
	
	
	List<DeliveryBoyProfile> findByApprovedFalse();
	
	
	

}
