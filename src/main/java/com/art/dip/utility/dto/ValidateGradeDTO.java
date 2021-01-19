package com.art.dip.utility.dto;

import lombok.Data;

@Data
public class ValidateGradeDTO {

    private String subjectName;

    private String fileName;

    private Integer mark;

    public ValidateGradeDTO(String subjectName, String fileName, Integer mark) {
        this.subjectName = subjectName;
        this.fileName = fileName;
        this.mark = mark;
    }
}
