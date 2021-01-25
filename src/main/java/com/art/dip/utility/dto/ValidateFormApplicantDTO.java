package com.art.dip.utility.dto;

import com.art.dip.annotation.Name;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ValidateFormApplicantDTO {

    @NotNull
    private Integer applicantId;

    private boolean valid;

    @NotNull
    @Email
    private String email;

    @Name
    private String fullName;

    private Set<CauseInvalid> causes;

    private String anotherCause;


}
