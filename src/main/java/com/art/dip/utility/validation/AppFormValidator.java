package com.art.dip.utility.validation;

import com.art.dip.annotation.ValidateAppForm;
import com.art.dip.model.Applicant;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.service.CurrentPersonInfoService;
import com.art.dip.utility.dto.ApplicantDTO;
import com.art.dip.utility.localization.MessageSourceService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

/*
    If applicant is already exist or faculty has expired
    return false
*/
public class AppFormValidator implements ConstraintValidator<ValidateAppForm, ApplicantDTO> {

    private final MessageSourceService mesService;

    private final FacultyInfoRepository repository;

    private final ApplicantRepository applicantRepository;

    private final CurrentPersonInfoService currentPersonInfoService;

    public AppFormValidator(MessageSourceService mesService, FacultyInfoRepository repository, ApplicantRepository applicantRepository, CurrentPersonInfoService currentPersonInfoService) {
        this.mesService = mesService;
        this.repository = repository;
        this.applicantRepository = applicantRepository;
        this.currentPersonInfoService = currentPersonInfoService;
    }

    @Override
    public boolean isValid(ApplicantDTO value, ConstraintValidatorContext context) {

        boolean valid = false;

        if (value != null && value.getFaculty() != null) {
            Integer currentLoggedPersonId = currentPersonInfoService.getCurrentLoggedPersonId();

            Applicant applicant = applicantRepository.findByPerson_Id(currentLoggedPersonId);

            valid = applicant == null && !repository.isFacultyExpired(LocalDateTime.now(), value.getFaculty().getId());
        }


        if (!valid) {
            context.buildConstraintViolationWithTemplate(mesService.getValidationMessageValidateFormApplicant())
                    .addPropertyNode("faculty")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
