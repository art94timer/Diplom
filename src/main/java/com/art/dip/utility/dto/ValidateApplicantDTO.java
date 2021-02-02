package com.art.dip.utility.dto;

import com.art.dip.model.Certificate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ValidateApplicantDTO {

    private Integer id;

    private String fullName;

    private String email;

    private List<ValidateGradeDTO> validateGradeDTO;

    private Certificate certificate;

}
