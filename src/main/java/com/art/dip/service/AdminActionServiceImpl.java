package com.art.dip.service;

import com.art.dip.model.Certificate;
import com.art.dip.model.InvalidApplicant;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.GradeRepository;
import com.art.dip.repository.InvalidApplicantRepository;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.art.dip.utility.Constants.*;

@Service
public class AdminActionServiceImpl {

    private final ApplicantRepository appRepository;

    private final FacultyRepository facRepository;

    private final GradeRepository gradeRepository;

    private final InvalidApplicantRepository invalidAppRepository;

    private final EmailService emailService;

    private final ApplicantPhotoService photoService;


    @Autowired
    public AdminActionServiceImpl(ApplicantRepository repository,
                                  FacultyRepository facRepository,
                                  GradeRepository gradeRepository,
                                  InvalidApplicantRepository invalidAppRepository, EmailService emailService, ApplicantPhotoService photoService) {
        this.appRepository = repository;
        this.facRepository = facRepository;
        this.gradeRepository = gradeRepository;
        this.invalidAppRepository = invalidAppRepository;
        this.emailService = emailService;
        this.photoService = photoService;
    }



    @Transactional
    public void handleListForms(ListValidateFormApplicantDTO list) {
        List<ValidateFormApplicantDTO> forms = list.getList();
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
        return facRepository.getAllFacultiesOnlyNameAndId();
    }

    public ModelAndView prepareValidateFormModelAndView(HttpSession session) {
        AdminSettings adminSettings = (AdminSettings) session.getAttribute(ADMIN_SETTINGS);
        if (adminSettings == null) {
            return new ModelAndView("redirect:/admin/prepare");
        }

        List<ValidateApplicantDTO> dto = getApplicant(adminSettings);
        return new ModelAndView("requests","applicants",dto).addObject("validateForm",new ListValidateFormApplicantDTO());
    }

    public List<ValidateApplicantDTO> getApplicant(AdminSettings adminSettings) {

        List<ValidateApplicantDTO> dto = appRepository.getApplicantForValidating(
                adminSettings.getFaculty(), PageRequest.of(0, adminSettings.getCountApplicants(),
                        Sort.by(Sort.Direction.ASC, "a.registrationTime")));

        return prepareApplicant(dto);
    }

    private List<ValidateApplicantDTO> prepareApplicant(List<ValidateApplicantDTO> dto) {
        dto.forEach(app -> {
            List<ValidateGradeDTO> grades = getGradesForApplicant(app.getId());
                grades.forEach(photoService::buildPath);
            app.setValidateGradeDTO(grades);


            photoService.buildPath(app.getCertificate());
        });
        return dto;
    }

    private List<ValidateGradeDTO> getGradesForApplicant(Integer id) {
        return gradeRepository.getGradesForApplicant(id);
    }


}

