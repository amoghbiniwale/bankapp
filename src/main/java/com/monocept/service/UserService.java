package com.monocept.service;

import com.monocept.dto.response.UserDetail;
import com.monocept.entities.User;

public interface UserService {
	UserDetail getUserDetail(String email);

}
