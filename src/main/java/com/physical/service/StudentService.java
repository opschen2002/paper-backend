package com.physical.service;

import com.physical.entity.Student;
import java.util.List;

public interface StudentService {
    Student getStudentById(String studentId);
    List<Student> getStudentsByClass(String className);
    List<Student> getStudentsByCollege(String college);
    List<Student> searchStudents(String keyword);
}