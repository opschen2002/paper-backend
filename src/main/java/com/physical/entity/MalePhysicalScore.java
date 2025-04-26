package com.physical.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "male_physical_scores")
public class MalePhysicalScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "test_date", nullable = false)
    private Date testDate;

    @Column(name = "run_1000m")
    private Double run1000m;      // 1000米跑(秒)
    
    @Column(name = "pull_up")
    private Integer pullUp;       // 引体向上(个)
    
    @Column(name = "total_score")
    private Double totalScore;    // 总分

    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }
}