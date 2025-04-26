package com.physical.service.impl;

import com.physical.entity.Student;
import com.physical.repository.StudentRepository;
import com.physical.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("学生不存在"));
    }

    @Override
    public List<Student> getStudentsByClass(String className) {
        return studentRepository.findByClassName(className);
    }

    @Override
    public List<Student> getStudentsByCollege(String college) {
        return studentRepository.findByCollege(college);
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        return studentRepository.searchStudents(keyword);
    }
}