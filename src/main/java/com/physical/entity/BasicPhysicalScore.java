package com.physical.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "basic_physical_scores")
public class BasicPhysicalScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "test_date", nullable = false)
    private Date testDate;

    private Double height;        // 身高(cm)
    private Double weight;        // 体重(kg)
    
    @Column(name = "run_50m")
    private Double run50m;        // 50米跑(秒)
    
    @Column(name = "long_jump")
    private Double longJump;      // 立定跳远(cm)
    
    @Column(name = "vital_capacity")
    private Integer vitalCapacity; // 肺活量(ml)
    
    @Column(name = "sit_and_reach")
    private Double sitAndReach;    // 位体前屈(cm)

    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }
}