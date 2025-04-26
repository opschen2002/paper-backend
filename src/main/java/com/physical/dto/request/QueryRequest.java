package com.physical.dto.request;

import lombok.Data;

@Data
public class QueryRequest {
    private String className;
    private String college;
    private String keyword;
    private String startDate;
    private String endDate;
}