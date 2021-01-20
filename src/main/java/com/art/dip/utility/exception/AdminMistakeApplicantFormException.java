package com.art.dip.utility.exception;

import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import lombok.Data;

import java.util.List;
@Data
public class AdminMistakeApplicantFormException extends RuntimeException {

    private List<ValidateFormApplicantDTO> mistakes;

    public AdminMistakeApplicantFormException(String message, List<ValidateFormApplicantDTO> mistakes) {
        super(message);
        this.mistakes = mistakes;
    }
}
