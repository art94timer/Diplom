package com.art.dip.utility.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValidateGradeDTO {

    private SubjectDTO subject;

    private String fileName;

    private Integer mark;

    public ValidateGradeDTO(SubjectDTO subject, String fileName, Integer mark) {
        this.subject = subject;
        this.fileName = fileName;
        this.mark = mark;
    }
}
