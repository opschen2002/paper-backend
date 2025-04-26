package com.physical.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "student_id", length = 20)
    private String studentId;  // 学号

    @Column(nullable = false, length = 50)
    private String name;      // 姓名

    @Column(nullable = false)
    private String gender;    // 性别

    @Column(name = "class_name", nullable = false, length = 50)
    private String className; // 班级

    @Column(nullable = false, length = 50)
    private String college;   // 学院

    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

}