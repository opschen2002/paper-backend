package com.physical.repository;

import com.physical.entity.BasicPhysicalScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BasicPhysicalScoreRepository extends JpaRepository<BasicPhysicalScore, Long> {
    /**
     * 根据学号查询最新成绩
     */
    Optional<BasicPhysicalScore> findFirstByStudentIdOrderByCreatedAtDesc(String studentId);
    
    /**
     * 根据学号和测试日期查询成绩
     */
    Optional<BasicPhysicalScore> findByStudentIdAndTestDate(String studentId, Date testDate);
    
    /**
     * 根据测试日期范围查询成绩
     */
    List<BasicPhysicalScore> findByTestDateBetween(Date startDate, Date endDate);
    
    /**
     * 查询某个学生的所有成绩历史
     */
    List<BasicPhysicalScore> findByStudentIdOrderByTestDateDesc(String studentId);
}