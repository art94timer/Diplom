package com.art.dip.utility.dto;

import com.art.dip.annotation.Name;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ValidateFormApplicantDTO {


    @Range(min = 1)
    private int applicantId;

    private boolean valid;

    @NotNull
    @Email
    private String email;
    @Name
    private String firstName;


    private Set<CauseInvalid> causes;

    private String anotherCause;


}
