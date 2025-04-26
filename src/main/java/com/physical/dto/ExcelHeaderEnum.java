package com.physical.dto;

import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Description: 表头对应字段名
 * @Author: zchstart
 * @Date: 2025/4/15 15:01
 */
public enum ExcelHeaderEnum {
    studentId("学号", "studentId"),
    name("姓名", "name"),
    className("性别", "gender"),
    height("班级", "className"),
    college("学院", "college"),
    weight("身高(cm)", "height"),
    run50m("体重(kg)", "weight"),
    longJump("立定跳远(cm)", "longJump"),
    vitalCapacity("肺活量(ml)", "vitalCapacity"),
    sitAndReach("坐位体前屈(cm)", "sitAndReach"),
    // 男生特有项目
    run1000m("长跑1000m(s)", "run1000m"),
    run800m("引体向上(个)", "pullUp"),
    // 女生特有项目
    pullUp("长跑800m(s)", "run800m"),
    sitUp("仰卧起坐(个)", "sitUp"),
    totalScore("总分", "totalScore"),
    testDate("测试日期", "testDate");

    private final String headerName;
    private final String fieldName;

    ExcelHeaderEnum(String headerName, String fieldName) {
        this.headerName = headerName;
        this.fieldName = fieldName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static String getFieldNameByHeaderName(String headerName) {
        if (StringUtils.isEmpty(headerName)) {
            return null;
        }
        ExcelHeaderEnum result = null;

        ExcelHeaderEnum[] values = ExcelHeaderEnum.values();
        for (ExcelHeaderEnum daoRuZdGlEnum : values) {
            if (daoRuZdGlEnum.getHeaderName().equals(headerName)) {
                result = daoRuZdGlEnum;
                break;
            }
        }
        if (result == null) {
            throw new RuntimeException("不存在的表头");
        }
        return result.getFieldName();
    }

}
