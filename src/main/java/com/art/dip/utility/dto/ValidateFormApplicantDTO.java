package com.art.dip.utility.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ValidateFormApplicantDTO {



    private int applicantId;

    private boolean valid;

    private String email;

    private String firstName;

    private Set<CauseInvalid> causes;

    private String anotherCause;


}
