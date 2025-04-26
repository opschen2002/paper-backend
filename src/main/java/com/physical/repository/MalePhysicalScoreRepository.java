package com.physical.repository;

import com.physical.entity.MalePhysicalScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MalePhysicalScoreRepository extends JpaRepository<MalePhysicalScore, Long> {
    /**
     * 根据学号查询最新成绩
     */
    Optional<MalePhysicalScore> findFirstByStudentIdOrderByCreatedAtDesc(String studentId);
    
    /**
     * 根据学号和测试日期查询成绩
     */
    Optional<MalePhysicalScore> findByStudentIdAndTestDate(String studentId, Date testDate);
    
    /**
     * 查询某个分数段的学生成绩
     */
    @Query("SELECT m FROM MalePhysicalScore m WHERE m.totalScore BETWEEN :minScore AND :maxScore")
    List<MalePhysicalScore> findByScoreRange(Double minScore, Double maxScore);
}