package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.vendor.Restaurant;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserId(Long userId);
	List<Order> findByRestaurantOrderByOrderdateDesc(Restaurant restaurant);
	List<Order>  findByDeliveryBoyIdAndOrderstatusIn(Long deliveryBoyId, List<OrderStatus> statuses);

}
