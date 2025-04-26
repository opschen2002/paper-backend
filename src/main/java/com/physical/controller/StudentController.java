package com.physical.controller;

import com.physical.dto.StudentScoreDTO;
import com.physical.entity.CommonResult;
import com.physical.entity.Student;
import com.physical.service.PhysicalScoreService;
import com.physical.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private PhysicalScoreService physicalScoreService;
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/info/{studentId}")
    public CommonResult<Student> getStudentInfo(@PathVariable String studentId) {
        try {
            Student student = studentService.getStudentById(studentId);
            return CommonResult.success(student);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }

    @GetMapping("/score/{studentId}")
    public CommonResult<StudentScoreDTO> getStudentScore(@PathVariable String studentId) {
        try {
            StudentScoreDTO score = physicalScoreService.getLatestScore(studentId);
            return CommonResult.success(score);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }
}


