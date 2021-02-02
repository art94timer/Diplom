package com.art.dip.service;

import com.art.dip.model.*;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.PersonRepository;
import com.art.dip.service.interfaces.ApplicantService;
import com.art.dip.utility.converter.ApplicantConverter;
import com.art.dip.utility.converter.FacultyConverter;
import com.art.dip.utility.converter.FacultyInfoConverter;
import com.art.dip.utility.dto.ApplicantDTO;
import com.art.dip.utility.dto.CertificateDTO;
import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;
import com.art.dip.utility.localization.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class ApplicantServiceImpl implements ApplicantService {

    private final PersonInfoService personInfoService;

    private final MessageSourceService mesService;

    private final ApplicantConverter applicantConverter;

    private final FacultyConverter facultyConverter;

    private final FacultyInfoConverter facultyInfoConverter;

    private final FacultyRepository facultyRepository;

    private final ApplicantRepository applicantRepository;

    private final PersonRepository personRepository;

    private final FacultyInfoRepository facultyInfoRepository;

    @Autowired
    public ApplicantServiceImpl(PersonInfoService personInfoService,
                                 FacultyRepository facultyRepository, ApplicantRepository applicantRepository,
                                ApplicantConverter applicantConverter,
                                PersonRepository personRepository,
                                FacultyInfoRepository facultyInfoRepository,
                                MessageSourceService mesService, FacultyConverter facultyConverter, FacultyInfoConverter facultyInfoConverter) {
        this.personInfoService = personInfoService;
        this.facultyRepository = facultyRepository;
        this.applicantRepository = applicantRepository;
        this.applicantConverter = applicantConverter;
        this.personRepository = personRepository;
        this.facultyInfoRepository = facultyInfoRepository;
        this.mesService = mesService;
        this.facultyConverter = facultyConverter;
        this.facultyInfoConverter = facultyInfoConverter;
    }

    public List<FacultyInfoDTO> getFaculties() {
        Locale locale = personInfoService.getCurrentLoggedPersonLocale();
        List<FacultyInfo> faculties = facultyInfoRepository.findAll();
        if(locale.getLanguage().equals("ru")) {
            return facultyInfoConverter.toRuFacultyInfoDTO(faculties);
        }
        return facultyInfoConverter.toEnFacultyInfoDTO(faculties);
    }

    public FacultyDTO getFacultyWithSubjects(Integer id) {
        Faculty faculty = facultyRepository.findFacultyByIdWithSubject(id);
        if (personInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facultyConverter.toRuFacultyDTO(faculty);
        } else {
            return facultyConverter.toEnFacultyDTO(faculty);

        }
   }

    @Transactional
    public void save(ApplicantDTO app) {
        Applicant applicant = prepareApplicant(app);
        if (applicant == null) {
            log.warn("Oops ... We got a problem here! Applicant's faculty or person not found!");
            return;
        }
        applicantRepository.save(applicant);
        log.info("Applicant with email: ".
                concat(applicant.getPerson().getEmail().
                concat(" registering on faculty ".
                concat(applicant.getFaculty().getName()))));
    }

    private Applicant prepareApplicant(ApplicantDTO dto) {
        Applicant applicant = applicantConverter.toEntity(dto);
        Optional<Faculty> f = facultyRepository.findById(applicant.getFaculty().getId());
        if (!f.isPresent()) {
            return null;
        }
        applicant.setFaculty(f.get());
        Integer currentLoggedPersonId = personInfoService.getCurrentLoggedPersonId();

        Optional<Person> p = personRepository.findById(currentLoggedPersonId);
        if (!p.isPresent()) {
            return null;
        }

        applicant.setPerson(p.get());
        applicant.setScore(calculateTotalScore(applicant));
        applicant.setRegistrationTime(LocalDateTime.now());
        applicant.setIsAccepted(false);
        applicant.getGrades().forEach(x->x.setApplicant(applicant));
        return applicant;
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

    @Override
    public String getWaitForAdminEmailMessage() {
        return mesService.getWaitForAdminEmailMessage();
    }

    @Override
    @Transactional
    public void dismissApplication() {
        Integer id = personInfoService.getCurrentLoggedPersonId();
        Applicant applicant = applicantRepository.findByPerson_Id(id);
        applicantRepository.delete(applicant);
        log.warn("Applicant with id ".concat(String.valueOf(applicant.getId()).concat(" cancel his application")));
    }
}
