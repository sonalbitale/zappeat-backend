package com.example.demo.service;

import com.example.demo.entity.OtpEntry;
import com.example.demo.repo.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepo;

    @Autowired
    private SmsProviderClient smsClient;

   

    public void sendOtp(String phone) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit

        OtpEntry entry = new OtpEntry();
        entry.setPhone(phone);
        entry.setOtp(otp);
        entry.setExpiresAt(LocalDateTime.now().plusDays(3));
        otpRepo.save(entry);

        smsClient.sendSms(phone, "Your FoodHub OTP is " + otp + ". Valid for 5 minutes. Do not share this with anyone.");
    }

    public boolean verifyOtp(String phone, String enteredOtp) {
        OtpEntry entry = otpRepo.findTopByPhoneOrderByIdDesc(phone)
            .orElseThrow(() -> new RuntimeException("No OTP requested for this number"));

        if (entry.isVerified()) {
            throw new RuntimeException("OTP already used, please request a new one");
        }
        if (LocalDateTime.now().isAfter(entry.getExpiresAt())) {
            throw new RuntimeException("OTP expired, please request a new one");
        }
        if (!entry.getOtp().equals(enteredOtp)) {
            throw new RuntimeException("Invalid OTP");
        }

        entry.setVerified(true);
        otpRepo.save(entry);
        return true;
    }
}