package com.art.dip.service;

import com.art.dip.model.InvalidApplicant;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.GradeRepository;
import com.art.dip.repository.InvalidApplicantRepository;
import com.art.dip.service.interfaces.EmailService;
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
public class AdminActionServiceImpl {

    private final ApplicantRepository appRepository;

    private final FacultyRepository facRepository;

    private final GradeRepository gradeRepository;

    private final InvalidApplicantRepository invalidAppRepository;

    private final EmailService emailService;

    private final ApplicantPhotoService photoService;

    private final MessageSourceService mesService;

    private final CurrentPersonInfoService currentPersonInfoService;


    @Autowired
    public AdminActionServiceImpl(ApplicantRepository repository,
                                  FacultyRepository facRepository,
                                  GradeRepository gradeRepository,
                                  InvalidApplicantRepository invalidAppRepository, EmailService emailService, ApplicantPhotoService photoService, MessageSourceService mesService, CurrentPersonInfoService currentPersonInfoService) {
        this.appRepository = repository;
        this.facRepository = facRepository;
        this.gradeRepository = gradeRepository;
        this.invalidAppRepository = invalidAppRepository;
        this.emailService = emailService;
        this.photoService = photoService;
        this.mesService = mesService;
        this.currentPersonInfoService = currentPersonInfoService;
    }



    @Transactional
    public void handleListForms(ListValidateFormApplicantDTO list) throws AdminMistakeApplicantFormException {
        List<ValidateFormApplicantDTO> forms = list.getList();
        List<ValidateFormApplicantDTO> mistakes = checkForAdminMistakes(forms);
        forms.removeAll(mistakes);
        validOrInvalidApplicants(forms);
        if (!mistakes.isEmpty()) {
            throw new AdminMistakeApplicantFormException(mesService.getInvalidFormApplicantNoCausesMessage(),mistakes);
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
        appRepository.validateApplicant(dto.getApplicantId());
        emailService.sendValidApplicantEmail(dto);
    }

    private void invalidApplicant(ValidateFormApplicantDTO dto) {
        String certificateFileName = appRepository.getCertificateByApplicantId(dto.getApplicantId()).getFileName();
        Map<String,String> grades = getGradesForApplicant(dto.getApplicantId()).stream().collect(Collectors.toMap
                (ValidateGradeDTO::getSubjectName,ValidateGradeDTO::getFileName));
        InvalidApplicant invalidApplicant = new InvalidApplicant(dto.getEmail(), grades, certificateFileName);
        invalidAppRepository.save(invalidApplicant);
        appRepository.delete(appRepository.findById(dto.getApplicantId()).get());
        emailService.sendInvalidApplicantEmail(dto);
    }


    public List<FacultyDTO> getFaculties() {
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facRepository.getAllRuFaculties();
        } else {
            return facRepository.getAllEnFaculties();
        }
    }

    public List<ValidateApplicantDTO> prepareValidateFormModelAndView(AdminSettings settings) {
        List<ValidateApplicantDTO> dto = appRepository.getApplicantForValidating(
                settings.getFaculty(), PageRequest.of(0, settings.getCountApplicants(),
                        Sort.by(Sort.Direction.ASC, "a.registrationTime")));

        return prepareApplicants(dto);

    }

    private List<ValidateApplicantDTO> prepareApplicants(List<ValidateApplicantDTO> dto) {
        dto.forEach(app -> {
            List<ValidateGradeDTO> grades = getGradesForApplicant(app.getId());
                grades.forEach(photoService::buildPath);
            app.setValidateGradeDTO(grades);


            photoService.buildPath(app.getCertificate());
        });
        return dto;
    }

    private List<ValidateGradeDTO> getGradesForApplicant(Integer id) {
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return gradeRepository.getRuGradesForApplicant(id);
        } else {
            return gradeRepository.getEnGradesForApplicant(id);
        }

    }

    public List<ValidateApplicantDTO> resolveMistakes(List<ValidateFormApplicantDTO> mistakes) {
        return prepareApplicants(mistakes.stream().map(x->appRepository.getApplicantForValidatingByEmail(x.getEmail()))
                .collect(Collectors.toList()));
    }
}

