package com.physical.controller;

import com.physical.dto.LoginDTO;
import com.physical.entity.CommonResult;
import com.physical.entity.User;
import com.physical.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private UserService userService;

    @GetMapping("/test")  // 添加测试端点
    public String test() {
        logger.info("测试端点被访问");
        return "API is working";
    }

    @PostMapping("/login")
    public CommonResult<User> login(@RequestBody LoginDTO loginDTO) {
        logger.info("收到登录请求，请求路径: /auth/login");
        logger.info("请求体: {}", loginDTO);
        try {
            User user = userService.login(loginDTO);
            logger.info("登录成功: {}", user);
            return CommonResult.success(user);
        } catch (Exception e) {
            logger.error("登录失败", e);
            return CommonResult.error(e.getMessage());
        }
    }
}