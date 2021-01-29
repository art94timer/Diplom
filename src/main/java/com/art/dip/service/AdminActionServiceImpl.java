package com.art.dip.service;

import com.art.dip.model.Applicant;
import com.art.dip.model.Faculty;
import com.art.dip.model.Grade;
import com.art.dip.model.InvalidApplicant;
import com.art.dip.repository.*;
import com.art.dip.service.interfaces.AdminActionService;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.converter.ApplicantConverter;
import com.art.dip.utility.converter.FacultyConverter;
import com.art.dip.utility.converter.GradeConverter;
import com.art.dip.utility.dto.*;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminActionServiceImpl implements AdminActionService {

    private final ApplicantRepository applicantRepository;

    private final CertificateRepository certificateRepository;

    private final FacultyRepository facultyRepository;

    private final GradeRepository gradeRepository;

    private final InvalidApplicantRepository invalidApplicantRepository;

    private final EmailService emailService;

    private final FacultyConverter facultyConverter;

    private final MessageSourceService mesService;

    private final PersonInfoService personInfoService;

    private final ApplicantConverter applicantConverter;

    private final GradeConverter gradeConverter;

    @Autowired
    public AdminActionServiceImpl(ApplicantRepository repository,
                                  CertificateRepository certificateRepository, FacultyRepository facultyRepository,
                                  GradeRepository gradeRepository,
                                  InvalidApplicantRepository invalidApplicantRepository,
                                  EmailService emailService, FacultyConverter facultyConverter,
                                  MessageSourceService mesService,
                                  PersonInfoService personInfoService,
                                  ApplicantConverter applicantConverter, GradeConverter gradeConverter) {
        this.applicantRepository = repository;
        this.certificateRepository = certificateRepository;
        this.facultyRepository = facultyRepository;
        this.gradeRepository = gradeRepository;
        this.invalidApplicantRepository = invalidApplicantRepository;
        this.emailService = emailService;
        this.facultyConverter = facultyConverter;
        this.mesService = mesService;
        this.personInfoService = personInfoService;
        this.applicantConverter = applicantConverter;
        this.gradeConverter = gradeConverter;
    }

    @Transactional
    public void handleListForms(ListValidateFormApplicantDTO list) throws AdminMistakeApplicantFormException {
        List<ValidateFormApplicantDTO> forms = list.getList();
        List<ValidateFormApplicantDTO> mistakes = checkForAdminMistakes(forms);
        forms.removeAll(mistakes);
        validOrInvalidApplicants(forms);
        if (!mistakes.isEmpty()) {
            throw new AdminMistakeApplicantFormException(mesService.getInvalidFormApplicantNoCausesMessage(), mistakes);
        }
    }

    //return list of forms with admin mistakes(only check for: if !valid and no one causes are selected)
    private List<ValidateFormApplicantDTO> checkForAdminMistakes(List<ValidateFormApplicantDTO> forms) {
        return forms.stream().filter(form ->
                !form.isValid() && (form.getCauses() == null && form.getAnotherCause() == null)
        ).collect(Collectors.toList());
    }

    private void validOrInvalidApplicants(List<ValidateFormApplicantDTO> forms) {
        forms.forEach(x -> {
            if (x.isValid()) {
                validApplicant(x);
            } else {
                invalidApplicant(x);
            }
        });
    }

    private void validApplicant(ValidateFormApplicantDTO dto) {
        applicantRepository.validateApplicant(dto.getApplicantId());
        emailService.sendValidApplicantEmail(dto);
    }


    private void invalidApplicant(ValidateFormApplicantDTO dto) {
        String certificateFileName = certificateRepository.getCertificateByApplicantId(dto.getApplicantId()).getFileName();
        Map<String, String> grades = getGradesForApplicant(dto.getApplicantId()).stream().collect(Collectors.toMap
                (key -> key.getSubject().getName(), ValidateGradeDTO::getFileName));
        InvalidApplicant invalidApplicant = new InvalidApplicant(dto.getEmail(), grades, certificateFileName);
        invalidApplicantRepository.save(invalidApplicant);
        applicantRepository.delete(applicantRepository.findById(dto.getApplicantId()).get());
        emailService.sendInvalidApplicantEmail(dto);
    }


    public List<FacultyDTO> getFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        if (personInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facultyConverter.toRuFacultyDTO(faculties);
        } else {
            return facultyConverter.toEnFacultyDTO(faculties);
        }
    }

    public List<ValidateApplicantDTO> prepareValidateFormModelAndView(AdminSettings settings) {
        List<Applicant> applicants = applicantRepository.getApplicantsForValidating(settings.getFaculty(), PageRequest.of(0,
                settings.getCountApplicants(), Sort.by(Sort.Direction.ASC, "registrationTime")));

        if (personInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return applicantConverter.toRuValidateApplicantDTO(applicants);
        } else {
            return applicantConverter.toEnValidateApplicantDTO(applicants);
        }

    }

    private List<ValidateGradeDTO> getGradesForApplicant(Integer id) {
        List<Grade> grades = gradeRepository.findAllByApplicant_Id(id);
        if (personInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return gradeConverter.toRuValidateGradeDTO(grades);
        } else {
            return gradeConverter.toEnValidateGradeDTO(grades);
        }
    }


    public List<ValidateApplicantDTO> resolveMistakes(List<ValidateFormApplicantDTO> mistakes) {
        List<Applicant> applicants = mistakes.stream().map(x ->
                applicantRepository.getApplicantForValidatingByEmail(x.getEmail()))
                .collect(Collectors.toList());
        if (personInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return applicantConverter.toRuValidateApplicantDTO(applicants);
        } else {
            return applicantConverter.toEnValidateApplicantDTO(applicants);
        }
    }
}

