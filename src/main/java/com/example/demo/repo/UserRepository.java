package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User>  findByUsername(String username);
	
	boolean existsByPhone(String phone);
	Optional<User> findByPhone(String phone);
	
	boolean existsByUsername(String username);
	boolean existsByEmailid(String emailid);

}
