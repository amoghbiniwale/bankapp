package com.monocept.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.dto.request.AccountRequest;
import com.monocept.dto.request.CreateBank;
import com.monocept.dto.request.RegisterRequest;
import com.monocept.dto.response.TransactionDetail;
import com.monocept.entities.Bank;
import com.monocept.entities.User;
import com.monocept.service.AdminService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor  
public class AdminController {
	
	  @Autowired
	  public AdminService adminService;
	  
	  @PostMapping("/register")
	  public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
		  return adminService.registerUser(request);
	  }
	  
	  @PostMapping("/create_acc")
	  public ResponseEntity<String> createAcc(@RequestBody AccountRequest request) {
		  return adminService.createAcc(request);
	  }
	  
	  @PostMapping("/create_bank")
	  public ResponseEntity<String> saveBank(@RequestBody CreateBank request) {
		return adminService.createBank(request);
	  }
	  
	  @GetMapping("/transactions")
	  public List<TransactionDetail> getAllTransactions() {
	      return adminService.getAllTransactions();
	  }
	  
	  @PostMapping("/registerAdmin")
	  public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {
		  return adminService.registerAdmin(request);
	  }
	  
	  @PutMapping("/update/bank/{id}")
		public Optional<Bank> updateBank(@RequestBody CreateBank bank,@PathVariable int id) {
			return adminService.updateBank(bank,id);
			
		}
	  @PutMapping("/update/user/{id}")
		public Optional<User> updateUser(@RequestBody RegisterRequest user,@PathVariable int id) {
			return adminService.updateUser(user,id);
			
		}
	  
	  @DeleteMapping("/bank/delete/{id}")
	  public void deleteBank(@PathVariable int id) {
		  adminService.deleteBank(id);
	  }
	  
	  @DeleteMapping("/user/delete/{id}")
	  public void deleteUser(@PathVariable int id) {
		  adminService.deleteUser(id);
	  }
	  
	  @GetMapping("/getAllUsers")
	  public List<User> getAllUsers() {
	      return adminService.getAllUsers();
	  }
}
