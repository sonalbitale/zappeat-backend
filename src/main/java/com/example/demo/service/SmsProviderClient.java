package com.example.demo.service;


public interface SmsProviderClient {
    void sendSms(String phone, String message);
}
