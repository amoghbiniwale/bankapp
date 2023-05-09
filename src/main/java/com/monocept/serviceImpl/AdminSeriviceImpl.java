package com.monocept.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monocept.dto.request.AccountRequest;
import com.monocept.dto.request.CreateBank;
import com.monocept.dto.request.RegisterRequest;
import com.monocept.dto.response.TransactionDetail;
import com.monocept.entities.Account;
import com.monocept.entities.Bank;
import com.monocept.entities.Role;
import com.monocept.entities.Transaction;
import com.monocept.entities.User;
import com.monocept.repo.AccountRepository;
import com.monocept.repo.BankRepository;
import com.monocept.repo.TransactionRepository;
import com.monocept.repo.UserRepository;
import com.monocept.service.AdminService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminSeriviceImpl implements AdminService {
	private final UserRepository userRepo;
    private final AccountRepository accRepo;
    private final BankRepository bankRepo;
    private final TransactionRepository transactionRepo;
    private final PasswordEncoder passwordEncoder;
	  
	@Override
	public ResponseEntity<String> registerUser(RegisterRequest request) {
		// Check if the email already exists in the database
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
	      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
		}
		var user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setIsActive(true);
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userRepo.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
	}

	@Override
	public ResponseEntity<String> createAcc(AccountRequest request) {
		var newAcc = new Account();
		Bank bank = bankRepo.findByBankId(request.getBankId()).orElse(null);
		User customer = userRepo.findByUserId(request.getCustomerId()).orElse(null);
		if (bank == null || customer == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find bank or customer"); 

		if (request.getAmount() < 1000)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Minimum balance criteria not met"); 
		
		newAcc.setBank(bank);
		newAcc.setUser(customer);
		newAcc.setAmount(request.getAmount());
		newAcc.setIsActive(true);
		accRepo.save(newAcc);
		return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
	}

	@Override
	public ResponseEntity<String> createBank(CreateBank request) {
		if (bankRepo.findByBankName(request.getName()).isPresent()) {
	      return ResponseEntity.status(HttpStatus.CONFLICT).body("Bank already exists");
		}
		var bank = new Bank();
		bank.setBankName(request.getName());
		bank.setAbbriviation(request.getAbbriviation());
		bankRepo.save(bank);
		return ResponseEntity.status(HttpStatus.CREATED).body("Bank created successfully");
	}

	@Override
	public List<TransactionDetail>  getAllTransactions() {
		List<TransactionDetail> data = new ArrayList<>();;
		for (Transaction t : transactionRepo.findAll()) {
			TransactionDetail currTransaction = new TransactionDetail();
			currTransaction.setAmount(t.getAmount());
			currTransaction.setCreatedOn(t.getCreatedOn());
			currTransaction.setTId(t.getTID());
			currTransaction.setTransactionType(t.getTransactionType().toString());
			currTransaction.setStatus(t.getStatus());
			currTransaction.setSenderAccNO(t.getAccount().getAccountNo());
			if (t.getReciver() != null)currTransaction.setReciverAccNO(t.getReciver().getAccountNo());
			data.add(currTransaction);
		}
		return data;
	}

	@Override
	public ResponseEntity<?> registerAdmin(RegisterRequest request) {
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
		      return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
			}
			var user = new User();
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setEmail(request.getEmail());
			user.setIsActive(true);
			user.setRole(Role.ADMIN);
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			userRepo.save(user);
			Map<String, Object> map = new HashMap<>();
			map.put("message", "Admin created successfully");
			map.put("status", "success");
			map.put("data", user);
			return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@Override
	@Transactional
	public Optional<Bank> updateBank(CreateBank bank, int id) {
		Optional<Bank> bank1 = bankRepo.findById(id);
		bank1.get().setBankName(bank.getName());
		bank1.get().setAbbriviation(bank.getAbbriviation());
		bankRepo.save(bank1.get());
		return bank1;
	}

	@Override
	public Optional<User> updateUser(RegisterRequest user, int id) {
		Optional<User> user1 = userRepo.findById(id);
		user1.get().setFirstName(user.getFirstName());
		user1.get().setLastName(user.getLastName());
		user1.get().setEmail(user.getEmail());
		user1.get().setPassword(user.getPassword());		
		userRepo.save(user1.get());
		return user1;
	}

	@Override
	public void deleteBank(int id) {
		bankRepo.deleteById(id);
	}

	@Override
	public void deleteUser(int id) {
		userRepo.deleteById(id);
		
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
		

}
