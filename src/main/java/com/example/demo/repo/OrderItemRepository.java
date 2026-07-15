package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order_Items;

@Repository
public interface OrderItemRepository extends JpaRepository<Order_Items, Long>{

}
