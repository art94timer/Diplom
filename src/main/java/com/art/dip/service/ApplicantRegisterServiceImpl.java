package com.art.dip.service;

import com.art.dip.model.Applicant;
import com.art.dip.model.Faculty;
import com.art.dip.model.Grade;
import com.art.dip.model.Person;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.GradeRepository;
import com.art.dip.repository.PersonRepository;
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

@Service
public class ApplicantRegisterServiceImpl implements ApplicantService {

    private final CurrentPersonInfoService currentPersonInfoService;

    private final FacultyRepository facRepository;

    private final ApplicantRepository appRepository;

    private final ApplicantConverter appConverter;

    private final PersonRepository perRepository;

    private final GradeRepository gradeRepository;


    @Autowired
    public ApplicantRegisterServiceImpl(CurrentPersonInfoService currentPersonInfoService,
                                        FacultyRepository facRepository, ApplicantRepository appRepository,
                                        ApplicantConverter appConverter,
                                        PersonRepository perRepository, GradeRepository gradeRepository) {
        this.currentPersonInfoService = currentPersonInfoService;
        this.facRepository = facRepository;
        this.appRepository = appRepository;
        this.appConverter = appConverter;
        this.perRepository = perRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Faculty> getFaculties() {
        return facRepository.findAll();


    }

    public Faculty getFacultyWithSubjectsById(Integer id) {
        return facRepository.findByIdWithSubjects(id);
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
