package com.monocept.dto.response;

import java.util.List;

import com.monocept.entities.Account;
import com.monocept.entities.Role;

import lombok.Data;

@Data
public class UserDetail {
	Long userId;
	String fname;
	String lname;
	String email;
	Boolean isActive;
	Role role;
	List<Account> accounts;
	Float totalBalance;
}
