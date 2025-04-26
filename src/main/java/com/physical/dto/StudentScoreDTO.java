package com.physical.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentScoreDTO {
    private String studentId;
    private String name;
    private String gender;
    private String className;
    private String college;
    private Double height;
    private Double weight;
    private Double run50m;
    private Double longJump;
    private Integer vitalCapacity;
    private Double sitAndReach;
    // 男生特有项目
    private Double run1000m;
    private Integer pullUp;
    // 女生特有项目
    private Double run800m;
    private Integer sitUp;
    private Double totalScore;
    private Date testDate;
}