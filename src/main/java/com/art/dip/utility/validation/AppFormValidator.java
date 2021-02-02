package com.art.dip.utility.validation;

import com.art.dip.annotation.ValidateAppForm;
import com.art.dip.model.Applicant;
import com.art.dip.model.FacultyInfo;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.service.PersonInfoService;
import com.art.dip.utility.dto.ApplicantDTO;
import com.art.dip.utility.localization.MessageSourceService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
    If applicant is already exist or faculty has expired
    return false
*/
public class AppFormValidator implements ConstraintValidator<ValidateAppForm, ApplicantDTO> {

    private final MessageSourceService mesService;

    private final FacultyInfoRepository repository;

    private final ApplicantRepository applicantRepository;

    private final PersonInfoService personInfoService;

    public AppFormValidator(MessageSourceService mesService, FacultyInfoRepository repository, ApplicantRepository applicantRepository, PersonInfoService personInfoService) {
        this.mesService = mesService;
        this.repository = repository;
        this.applicantRepository = applicantRepository;
        this.personInfoService = personInfoService;
    }

    @Override
    public boolean isValid(ApplicantDTO value, ConstraintValidatorContext context) {

        boolean valid = false;

        if (value != null && value.getFaculty() != null) {
            Integer currentLoggedPersonId = personInfoService.getCurrentLoggedPersonId();

            Applicant applicant = applicantRepository.findByPerson_Id(currentLoggedPersonId);

            FacultyInfo facultyInfo = repository.findByFaculty_Id(value.getFaculty().getId());
            valid = applicant == null && facultyInfo.isAvailable();
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
