package com.physical.repository;

import com.physical.entity.FemalePhysicalScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FemalePhysicalScoreRepository extends JpaRepository<FemalePhysicalScore, Long> {
    /**
     * 根据学号查询最新成绩
     */
    Optional<FemalePhysicalScore> findFirstByStudentIdOrderByCreatedAtDesc(String studentId);
    
    /**
     * 根据学号和测试日期查询成绩
     */
    Optional<FemalePhysicalScore> findByStudentIdAndTestDate(String studentId, Date testDate);
    
    /**
     * 查询某个分数段的学生成绩
     */
    @Query("SELECT f FROM FemalePhysicalScore f WHERE f.totalScore BETWEEN :minScore AND :maxScore")
    List<FemalePhysicalScore> findByScoreRange(Double minScore, Double maxScore);
}