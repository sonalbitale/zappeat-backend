
package com.example.demo.dto;

public class LoginResponsedto {

	 private String message;
	 private int status;
	 private String token;
	 public String getMessage() {
		 return message;
	 }
	 public void setMessage(String message) {
		 this.message = message;
	 }
	 public int getStatus() {
		 return status;
	 }
	 public void setStatus(int status) {
		 this.status = status;
	 }
	 public String getToken() {
		 return token;
	 }
	 public void setToken(String token) {
		 this.token = token;
	 }
	 public LoginResponsedto(String message, int status, String token) {
		super();
		this.message = message;
		this.status = status;
		this.token = token;
	 }
	 
}
