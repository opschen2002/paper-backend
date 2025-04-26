package com.physical.repository;

import com.physical.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;   // 添加这行导入
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    /**
     * 根据用户名和密码查询用户
     */
    Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * 根据角色查询用户
     */
    List<User> findByRole(User.UserRole role);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);
}