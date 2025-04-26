package com.physical.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(length = 20)
    private String username;  // 用户名/学号

    @Column(nullable = false, length = 20)
    private String password;  // 密码

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;    // 用户角色

    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    public enum UserRole {
        admin, teacher, student
    }
}