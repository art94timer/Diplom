package com.art.dip.utility.validation;

import com.art.dip.annotation.Password;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, PersonDTO> {

    private final MessageSourceService mesService;

    @Autowired
    public PasswordValidator(MessageSourceService mesService) {
        this.mesService = mesService;
    }

    @Override
    public boolean isValid(PersonDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        boolean valid = value.getPassword() != null && value.getConfirmPassword() != null
                && value.getPassword().equals(value.getConfirmPassword());

        if (!valid) {
            context.buildConstraintViolationWithTemplate(mesService.getValidationMessagePasswordNotMatched())
                    .addPropertyNode("password")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }

}
