package com.physical.service.impl;

import com.physical.dto.LoginDTO;
import com.physical.entity.User;
import com.physical.repository.UserRepository;
import com.physical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(LoginDTO loginDTO) {
        return userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .filter(user -> user.getRole().toString().equals(loginDTO.getRole()))
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
    }

    @Override
    public boolean validateUser(String username, String password, String role) {
        return userRepository.findByUsernameAndPassword(username, password)
                .filter(user -> user.getRole().toString().equals(role))
                .isPresent();
    }
}