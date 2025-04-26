package com.physical.service;

import com.physical.dto.LoginDTO;
import com.physical.entity.User;

public interface UserService {
    User login(LoginDTO loginDTO);
    boolean validateUser(String username, String password, String role);
}