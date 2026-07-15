package com.example.demo.repo;

import com.example.demo.entity.OtpEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntry, Long> {
    Optional<OtpEntry> findTopByPhoneOrderByIdDesc(String phone);
}