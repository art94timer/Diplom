package com.art.dip.utility.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/*
For Admin Work::requests.html
 */

@Data
public class ListValidateFormApplicantDTO {

    @Size(min= 1)
    @Valid
    private List<ValidateFormApplicantDTO> list;
}
