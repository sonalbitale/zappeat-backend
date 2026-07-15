package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class Fast2SmsClient  implements SmsProviderClient{

	 @Value("${fast2sms.api.key}")
	    private String apiKey;

	    private final RestTemplate restTemplate = new RestTemplate();

	    @Override
	    public void sendSms(String phone, String message) {
	        String url = UriComponentsBuilder
	        		
	        	.fromUriString("https://www.fast2sms.com/dev/bulkV2")	          
	        	.queryParam("authorization", apiKey)
	            .queryParam("route", "q")
	            .queryParam("message", message)
	            .queryParam("numbers", phone)
	            .toUriString();

	        try {
	            String response = restTemplate.getForObject(url, String.class);
	            System.out.println("Fast2SMS response: " + response);
	        } catch (Exception e) {
	            System.err.println("Failed to send SMS: " + e.getMessage());
	            throw new RuntimeException("SMS sending failed, please try again");
	        }
	    }

}
