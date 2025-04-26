package com.physical.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.physical.dto.ExcelHeaderEnum;
import com.physical.dto.GenderEnum;
import com.physical.dto.StudentScoreDTO;
import com.physical.entity.BasicPhysicalScore;
import com.physical.entity.FemalePhysicalScore;
import com.physical.entity.MalePhysicalScore;
import com.physical.entity.Student;
import com.physical.repository.BasicPhysicalScoreRepository;
import com.physical.repository.FemalePhysicalScoreRepository;
import com.physical.repository.MalePhysicalScoreRepository;
import com.physical.repository.StudentRepository;
import com.physical.service.PhysicalScoreService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhysicalScoreServiceImpl implements PhysicalScoreService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BasicPhysicalScoreRepository basicScoreRepository;
    @Autowired
    private MalePhysicalScoreRepository maleScoreRepository;
    @Autowired
    private FemalePhysicalScoreRepository femaleScoreRepository;

    @Override
    public StudentScoreDTO getLatestScore(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("学生不存在"));

        BasicPhysicalScore basicScore = basicScoreRepository
                .findFirstByStudentIdOrderByCreatedAtDesc(studentId)
                .orElseThrow(() -> new RuntimeException("未找到体测成绩"));

        StudentScoreDTO dto = new StudentScoreDTO();
        // 设置基本信息
        dto.setStudentId(student.getStudentId());
        dto.setName(student.getName());
        dto.setGender(student.getGender());
        dto.setClassName(student.getClassName());
        dto.setCollege(student.getCollege());

        // 设置基础体测成绩
        dto.setHeight(basicScore.getHeight());
        dto.setWeight(basicScore.getWeight());
        dto.setRun50m(basicScore.getRun50m());
        dto.setLongJump(basicScore.getLongJump());
        dto.setVitalCapacity(basicScore.getVitalCapacity());
        dto.setSitAndReach(basicScore.getSitAndReach());
        dto.setTestDate(basicScore.getTestDate());

        // 根据性别设置特有项目成绩
        if (student.getGender().equals(GenderEnum.MALE.getName())) {
            MalePhysicalScore maleScore = maleScoreRepository
                    .findFirstByStudentIdOrderByCreatedAtDesc(studentId)
                    .orElseThrow(() -> new RuntimeException("未找到男生体测成绩"));
            dto.setRun1000m(maleScore.getRun1000m());
            dto.setPullUp(maleScore.getPullUp());
            dto.setTestDate(new Date());
            dto.setTotalScore(maleScore.getTotalScore());
        } else {
            FemalePhysicalScore femaleScore = femaleScoreRepository
                    .findFirstByStudentIdOrderByCreatedAtDesc(studentId)
                    .orElseThrow(() -> new RuntimeException("未找到女生体测成绩"));
            dto.setRun800m(femaleScore.getRun800m());
            dto.setSitUp(femaleScore.getSitUp());
            dto.setTestDate(new Date());
            dto.setTotalScore(femaleScore.getTotalScore());
        }

        return dto;
    }

    @Override
    public List<StudentScoreDTO> getScoresByClass(String className) {
        return getScoresByClass(className, null);
    }

    @Override
    public List<StudentScoreDTO> getScoresByClass(String className, String studentId) {
        List<Student> students;
        if (StringUtils.isEmpty(studentId)) {
            students = studentRepository.findByClassName(className);
        } else {
            students = studentRepository.findAllByStudentIdIn(Collections.singleton(studentId));
        }
        List<StudentScoreDTO> scores = new ArrayList<>();
        for (Student student : students) {
            try {
                scores.add(getLatestScore(student.getStudentId()));
            } catch (RuntimeException e) {
                // 如果某个学生没有成绩，继续处理下一个学生
                continue;
            }
        }
        return scores;
    }

    @Override
    @Transactional
    public void saveScore(StudentScoreDTO scoreDTO) {
        // 保存基础体测成绩
        BasicPhysicalScore basicScore = new BasicPhysicalScore();
        basicScore.setStudentId(scoreDTO.getStudentId());
        basicScore.setTestDate(scoreDTO.getTestDate());
        basicScore.setHeight(scoreDTO.getHeight());
        basicScore.setWeight(scoreDTO.getWeight());
        basicScore.setRun50m(scoreDTO.getRun50m());
        basicScore.setLongJump(scoreDTO.getLongJump());
        basicScore.setVitalCapacity(scoreDTO.getVitalCapacity());
        basicScore.setSitAndReach(scoreDTO.getSitAndReach());
        basicScoreRepository.save(basicScore);

        // 根据学生性别保存特有项目成绩
        Student student = studentRepository.findById(scoreDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("学生不存在"));

        if (student.getGender().equals(GenderEnum.MALE.getName())) {
            MalePhysicalScore maleScore = new MalePhysicalScore();
            maleScore.setStudentId(scoreDTO.getStudentId());
            maleScore.setTestDate(scoreDTO.getTestDate());
            maleScore.setRun1000m(scoreDTO.getRun1000m());
            maleScore.setPullUp(scoreDTO.getPullUp());
            maleScore.setTotalScore(scoreDTO.getTotalScore());
            maleScoreRepository.save(maleScore);
        } else {
            FemalePhysicalScore femaleScore = new FemalePhysicalScore();
            femaleScore.setStudentId(scoreDTO.getStudentId());
            femaleScore.setTestDate(scoreDTO.getTestDate());
            femaleScore.setRun800m(scoreDTO.getRun800m());
            femaleScore.setSitUp(scoreDTO.getSitUp());
            femaleScore.setTotalScore(scoreDTO.getTotalScore());
            femaleScoreRepository.save(femaleScore);
        }
    }

    @Override
    public List<StudentScoreDTO> getScoresByDateRange(Date startDate, Date endDate) {
        List<BasicPhysicalScore> basicScores = basicScoreRepository.findByTestDateBetween(startDate, endDate);
        List<StudentScoreDTO> scores = new ArrayList<>();
        for (BasicPhysicalScore basicScore : basicScores) {
            try {
                scores.add(getLatestScore(basicScore.getStudentId()));
            } catch (RuntimeException ignored) {
            }
        }
        return scores;
    }

    @Override
    public List<String> getClassNameList() {
        return studentRepository.getClassNameList();
    }

    @Override
    @Transactional
    public void importScore(XSSFWorkbook workbook) {
        Iterator<Sheet> scoreSheet = workbook.sheetIterator();
        while (scoreSheet.hasNext()) {
            Sheet sheet = scoreSheet.next();
            List<StudentScoreDTO> studentScoreList = extractionData(sheet);

            studentScoreList.forEach(item -> {
                if (StringUtils.isEmpty(item.getGender())) {
                    throw new RuntimeException("性别是必填的");
                }
                if (StringUtils.isEmpty(item.getStudentId())) {
                    throw new RuntimeException("学号是必填的");
                }
            });

            // 找出不存在的学生
            Set<String> studentSet = studentScoreList.stream().map(StudentScoreDTO::getStudentId).collect(Collectors.toSet());
            List<Student> studentIn = studentRepository.findAllByStudentIdIn(studentSet);
            Set<String> studentIdIn = studentIn.stream().map(Student::getStudentId).collect(Collectors.toSet());

            List<BasicPhysicalScore> basicPhysicalScores = new ArrayList<>();
            List<MalePhysicalScore> malePhysicalScores = new ArrayList<>();
            List<FemalePhysicalScore> femalePhysicalScores = new ArrayList<>();
            for (StudentScoreDTO studentScore : studentScoreList) {

                if (CollUtil.isEmpty(studentIdIn) || !studentIdIn.contains(studentScore.getStudentId())) {
                    // 创建学生
                    Student student = new Student();
                    BeanUtil.copyProperties(studentScore, student);
                    studentRepository.save(student);
                }

                if (studentScore.getTestDate() == null) {
                    studentScore.setTestDate(new Date());
                }

                BasicPhysicalScore basicPhysicalScore = new BasicPhysicalScore();
                BeanUtil.copyProperties(studentScore, basicPhysicalScore);
                basicPhysicalScores.add(basicPhysicalScore);
                switch (GenderEnum.ofCode(studentScore.getGender())) {
                    case MALE:
                        MalePhysicalScore malePhysicalScore = new MalePhysicalScore();
                        BeanUtil.copyProperties(studentScore, malePhysicalScore);
                        malePhysicalScores.add(malePhysicalScore);
                        break;
                    case FEMALE:
                        FemalePhysicalScore femalePhysicalScore = new FemalePhysicalScore();
                        BeanUtil.copyProperties(studentScore, femalePhysicalScore);
                        femalePhysicalScores.add(femalePhysicalScore);
                        break;
                    default:
                        throw new IllegalStateException("错误的性别");
                }
            }
            basicScoreRepository.saveAll(basicPhysicalScores);
            maleScoreRepository.saveAll(malePhysicalScores);
            femaleScoreRepository.saveAll(femalePhysicalScores);
        }
    }

    private List<StudentScoreDTO> extractionData(Sheet sheet) {
        int numberOfRows = sheet.getPhysicalNumberOfRows();
        List<Map<String, Object>> result = new ArrayList<>(numberOfRows);

        Row row0 = sheet.getRow(0);
        for (int rownum = 1; rownum < numberOfRows; rownum++) {
            Row row = sheet.getRow(rownum);
            Map<String, Object> resultMap = new HashMap<>();

            if (Objects.isNull(row)) {
                continue;
            }
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell next = cellIterator.next();
                int cellIndex = next.getColumnIndex();
                Cell headerCell = row0.getCell(cellIndex);
                String fieldName = ExcelHeaderEnum.getFieldNameByHeaderName(headerCell.getStringCellValue());
                resultMap.put(fieldName, resolveVarCharValue(next));
            }
            result.add(resultMap);
        }
        String jsonString = JSON.toJSONString(result);
        return JSONObject.parseArray(jsonString, StudentScoreDTO.class);
    }

    private String resolveVarCharValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        if (CellType.NUMERIC.equals(cellType)) {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(cell.getNumericCellValue());
        }
        return cell.getStringCellValue().trim();
    }

}