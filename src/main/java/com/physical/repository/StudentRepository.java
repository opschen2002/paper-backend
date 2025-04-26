package com.physical.repository;

import com.physical.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    /**
     * 根据班级查询学生列表
     */
    List<Student> findByClassName(String className);
    
    /**
     * 根据学院查询学生列表
     */
    List<Student> findByCollege(String college);
    
    /**
     * 根据性别查询学生列表
     */
    List<Student> findByGender(String gender);

    /**
     * 根据性别查询学生列表
     */
    List<Student> findAllByStudentIdIn(Collection<String> studentIdList);
    
    /**
     * 模糊查询学生信息
     */
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.studentId LIKE %:keyword%")
    List<Student> searchStudents(String keyword);


    @Query("SELECT className FROM Student group by className")
    List<String> getClassNameList();
    
    /**
     * 根据学院和班级查询学生
     */
    List<Student> findByCollegeAndClassName(String college, String className);
}