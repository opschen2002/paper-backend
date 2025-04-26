package com.physical.controller;

import com.physical.dto.StudentScoreDTO;
import com.physical.entity.CommonResult;
import com.physical.service.PhysicalScoreService;
import com.physical.service.StudentService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PhysicalScoreService physicalScoreService;
    
    @Autowired
    private StudentService studentService;

    /**
     * 导入成绩信息
     * 该接口用于接收客户端上传的Excel文件，并将成绩信息导入到系统中
     *
     * @param file 上传的Excel文件，通过@RequestParam注解指定为请求参数中的'file'
     * @return 返回一个CommonResult对象，表示导入操作的结果
     *         如果导入成功，返回成功结果，结果数据为null
     *         如果导入失败，返回错误结果，结果数据包含错误信息
     */
    @PostMapping("/score/import")
    public CommonResult<?> importScore(@RequestParam("file") MultipartFile file) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            // 成绩导入
            physicalScoreService.importScore(workbook);
            return CommonResult.success(null);
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

    @PutMapping("/score")
    public CommonResult<?> updateScore(@RequestBody StudentScoreDTO scoreDTO) {
        try {
            physicalScoreService.saveScore(scoreDTO);
            return CommonResult.success(null);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }

    @GetMapping("/getClassNameList")
    public CommonResult<List<String>> getClassNameList() {
        try {
            return CommonResult.success(physicalScoreService.getClassNameList());
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }
}