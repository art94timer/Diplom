package com.art.dip.utility.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValidateGradeDTO {

    private SubjectDTO subject;

    private String fileName;

    private Integer mark;


}
