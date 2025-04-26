package com.physical.service;

import com.physical.dto.StudentScoreDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;
import java.util.List;

public interface PhysicalScoreService {
    StudentScoreDTO getLatestScore(String studentId);
    List<StudentScoreDTO> getScoresByClass(String className);
    List<StudentScoreDTO> getScoresByClass(String className, String studentId);
    List<StudentScoreDTO> getScoresByDateRange(Date startDate, Date endDate);
    void saveScore(StudentScoreDTO scoreDTO);

    void importScore(XSSFWorkbook workbook);

    List<String> getClassNameList();

}