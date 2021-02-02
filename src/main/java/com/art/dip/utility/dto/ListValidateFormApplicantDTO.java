package com.art.dip.utility.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;


@Data
public class ListValidateFormApplicantDTO {

    @Size(min= 1)
    @Valid
    private List<ValidateFormApplicantDTO> list;
}
