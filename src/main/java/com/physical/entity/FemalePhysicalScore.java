package com.physical.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "female_physical_scores")
public class FemalePhysicalScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "test_date", nullable = false)
    private Date testDate;

    @Column(name = "run_800m")
    private Double run800m;       // 800米跑(秒)
    
    @Column(name = "sit_up")
    private Integer sitUp;        // 仰卧起坐(个)
    
    @Column(name = "total_score")
    private Double totalScore;    // 总分

    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }
}