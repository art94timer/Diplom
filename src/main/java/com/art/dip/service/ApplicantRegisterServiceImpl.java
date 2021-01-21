package com.art.dip.service;

import com.art.dip.model.Applicant;
import com.art.dip.model.Grade;
import com.art.dip.model.Person;
import com.art.dip.repository.*;
import com.art.dip.service.interfaces.ApplicantService;
import com.art.dip.utility.converter.ApplicantConverter;
import com.art.dip.utility.dto.ApplicantDTO;
import com.art.dip.utility.dto.CertificateDTO;
import com.art.dip.utility.dto.FacultyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ApplicantRegisterServiceImpl implements ApplicantService {

    private final CurrentPersonInfoService currentPersonInfoService;

    private final FacultyRepository facRepository;

    private final ApplicantRepository appRepository;

    private final ApplicantConverter appConverter;

    private final PersonRepository perRepository;

    private final GradeRepository gradeRepository;

    private final SubjectRepository subjectRepository;

    @Autowired
    public ApplicantRegisterServiceImpl(CurrentPersonInfoService currentPersonInfoService,
                                        FacultyRepository facRepository, ApplicantRepository appRepository,
                                        ApplicantConverter appConverter,
                                        PersonRepository perRepository, GradeRepository gradeRepository, SubjectRepository subjectRepository) {
        this.currentPersonInfoService = currentPersonInfoService;
        this.facRepository = facRepository;
        this.appRepository = appRepository;
        this.appConverter = appConverter;
        this.perRepository = perRepository;
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<FacultyDTO> getFaculties() {

        Locale locale = currentPersonInfoService.getCurrentLoggedPersonLocale();
        if(locale.getLanguage().equals("ru")) {
            return facRepository.getAllRuFaculties();
        }

        return facRepository.getAllEnFaculties();


    }

    public FacultyDTO getFacultyWithSubjects(Integer id) {
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            FacultyDTO ruFaculty = facRepository.getRuFacultyById(id);
            ruFaculty.setSubjects(subjectRepository.findRuSubjectsByFacultyId(id));
            return ruFaculty;
        } else {
            FacultyDTO enFaculty = facRepository.getEnFacultyById(id);
            enFaculty.setSubjects(subjectRepository.findEnSubjectsByFacultyId(id));
            return enFaculty;
        }
   }

    @Transactional
    public void save(ApplicantDTO app) {
        Applicant applicant = appConverter.toEntity(app);
        applicant.setFaculty(facRepository.findById(applicant.getFaculty().getId()).get());
        Integer currentLoggedPersonId = currentPersonInfoService.getCurrentLoggedPersonId();
        Person person = perRepository.findById(currentLoggedPersonId).get();
        applicant.setPerson(person);
        applicant.setScore(calculateTotalScore(applicant));
        applicant.setRegistrationTime(LocalDateTime.now());
        applicant.setIsAccepted(false);
        appRepository.save(applicant);
        applicant.getGrades().forEach(x -> {
            x.setApplicant(applicant);
            gradeRepository.save(x);
        });

    }

    private int calculateTotalScore(Applicant applicant) {
        return applicant.getGrades().stream().mapToInt(Grade::getMark).sum() + applicant.getCertificate().getMark();
    }


    public ApplicantDTO prepareApplicantDTO(ApplicantDTO applicantDTO) {
        ApplicantDTO applicant;
        if (applicantDTO != null && applicantDTO.getFaculty() != null && applicantDTO.getCertificate() != null && applicantDTO.getGrades() != null) {
            applicant = applicantDTO;
        } else {
            applicant = new ApplicantDTO();
            applicant.setCertificate(new CertificateDTO());
            applicant.setFaculty(new FacultyDTO());
            applicant.setGrades(new ArrayList<>());
        }
        return applicant;
    }
}
