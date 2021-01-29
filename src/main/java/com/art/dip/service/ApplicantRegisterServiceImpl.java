package com.art.dip.service;

import com.art.dip.model.*;
import com.art.dip.repository.*;
import com.art.dip.service.interfaces.ApplicantService;
import com.art.dip.utility.converter.ApplicantConverter;
import com.art.dip.utility.converter.FacultyConverter;
import com.art.dip.utility.converter.FacultyInfoConverter;
import com.art.dip.utility.dto.ApplicantDTO;
import com.art.dip.utility.dto.CertificateDTO;
import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ApplicantRegisterServiceImpl implements ApplicantService {

    private final PersonInfoService personInfoService;

    private final FacultyRepository facultyRepository;

    private final ApplicantRepository applicantRepository;

    private final ApplicantConverter applicantConverter;

    private final PersonRepository personRepository;

    private final GradeRepository gradeRepository;

    private final FacultyInfoRepository facultyInfoRepository;

    private final MessageSourceService mesService;

    private final FacultyConverter facultyConverter;

    private final FacultyInfoConverter facultyInfoConverter;

    @Autowired
    public ApplicantRegisterServiceImpl(PersonInfoService personInfoService,
                                        FacultyRepository facultyRepository, ApplicantRepository applicantRepository,
                                        ApplicantConverter applicantConverter,
                                        PersonRepository personRepository, GradeRepository gradeRepository,
                                        FacultyInfoRepository facultyInfoRepository,
                                        MessageSourceService mesService, FacultyConverter facultyConverter, FacultyInfoConverter facultyInfoConverter) {
        this.personInfoService = personInfoService;
        this.facultyRepository = facultyRepository;
        this.applicantRepository = applicantRepository;
        this.applicantConverter = applicantConverter;
        this.personRepository = personRepository;
        this.gradeRepository = gradeRepository;
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
        Applicant applicant = applicantConverter.toEntity(app);
        applicant.setFaculty(facultyRepository.findById(applicant.getFaculty().getId()).get());
        Integer currentLoggedPersonId = personInfoService.getCurrentLoggedPersonId();
        Person person = personRepository.findById(currentLoggedPersonId).get();
        applicant.setPerson(person);
        applicant.setScore(calculateTotalScore(applicant));
        applicant.setRegistrationTime(LocalDateTime.now());
        applicant.setIsAccepted(false);
        applicant.getGrades().forEach(x->x.setApplicant(applicant));
        applicantRepository.save(applicant);


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

    }


}
