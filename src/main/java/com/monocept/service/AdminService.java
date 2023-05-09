package com.monocept.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.monocept.dto.request.AccountRequest;
import com.monocept.dto.request.CreateBank;
import com.monocept.dto.request.RegisterRequest;
import com.monocept.dto.response.TransactionDetail;
import com.monocept.entities.Bank;
import com.monocept.entities.User;


public interface AdminService {
	
	ResponseEntity<String> registerUser(RegisterRequest request);
	
	ResponseEntity<String> createAcc(AccountRequest request);
	
	ResponseEntity<String> createBank(CreateBank request);
	
	List<TransactionDetail> getAllTransactions();
	
	ResponseEntity<?> registerAdmin(RegisterRequest request);

	Optional<Bank> updateBank(CreateBank bank, int id);

	Optional<User> updateUser(RegisterRequest user, int id);

	void deleteBank(int id);

	void deleteUser(int id);

	List<User> getAllUsers();
}
