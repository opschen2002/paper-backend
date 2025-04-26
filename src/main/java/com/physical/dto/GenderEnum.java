package com.physical.dto;

/**
 * @Description:
 * @Author: zchstart
 * @Date: 2025/4/15 15:29
 */
public enum GenderEnum {
    MALE("男"),
    FEMALE("女");

    private String name;

    GenderEnum(String name) {
        this.name = name;
    }

    public static GenderEnum ofCode(String gender) {
        GenderEnum result = null;
        GenderEnum[] values = GenderEnum.values();
        for (GenderEnum genderEnum : values) {
            if (genderEnum.getName().equals(gender)) {
                result = genderEnum;
                break;
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }
}
