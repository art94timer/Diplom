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

    public ValidateApplicantDTO(Integer id,String fullName,  String email,Certificate certificate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.certificate = certificate;
    }
}
