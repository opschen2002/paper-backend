package com.physical.controller;

import cn.hutool.core.date.DateUtil;
import com.physical.dto.StudentScoreDTO;
import com.physical.dto.request.QueryRequest;
import com.physical.entity.CommonResult;
import com.physical.entity.Student;
import com.physical.service.PhysicalScoreService;
import com.physical.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private PhysicalScoreService physicalScoreService;
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public CommonResult<List<Student>> queryStudents(QueryRequest request) {
        try {
            List<Student> students;
            if (request.getClassName() != null) {
                students = studentService.getStudentsByClass(request.getClassName());
            } else if (request.getCollege() != null) {
                students = studentService.getStudentsByCollege(request.getCollege());
            } else if (request.getKeyword() != null) {
                students = studentService.searchStudents(request.getKeyword());
            } else {
                return CommonResult.error("请提供查询条件");
            }
            return CommonResult.success(students);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }

    @GetMapping("/scores/class/{className}")
    public CommonResult<List<StudentScoreDTO>> getScoresByClass(@PathVariable String className) {
        try {
            List<StudentScoreDTO> scores = physicalScoreService.getScoresByClass(className);
            return CommonResult.success(scores);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }

    @GetMapping("/scores/byClass")
    public CommonResult<List<StudentScoreDTO>> byClass(@RequestParam("className") String className,
                                                       @RequestParam(value = "studentId", required = false) String studentId) {
        try {
            List<StudentScoreDTO> scores = physicalScoreService.getScoresByClass(className, studentId);
            return CommonResult.success(scores);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }

    @GetMapping("/scores/date-range")
    public CommonResult<List<StudentScoreDTO>> getScoresByDateRange(@RequestParam("startDate") String startDate,
                                                                    @RequestParam("endDate") String endDate) {
        try {
            Date start = DateUtil.parseDateTime(startDate);
            Date end = DateUtil.parseDateTime(endDate);
            List<StudentScoreDTO> scores = physicalScoreService.getScoresByDateRange(start, end);
            return CommonResult.success(scores);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }
}