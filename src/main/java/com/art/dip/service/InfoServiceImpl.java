package com.art.dip.service;

import com.art.dip.model.*;
import com.art.dip.repository.*;
import com.art.dip.service.interfaces.InfoService;
import com.art.dip.utility.converter.FacultyInfoConverter;
import com.art.dip.utility.dto.AccountInfoDTO;
import com.art.dip.utility.dto.ApplicationStatus;
import com.art.dip.utility.dto.FacultyInfoDTO;
import com.art.dip.utility.localization.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Service
@Slf4j
public class InfoServiceImpl implements InfoService {

    private final FacultyRepository facultyRepository;

    private final PersonInfoService personInfoService;

    private final FacultyInfoRepository facultyInfoRepository;

    private final FacultyInfoConverter facultyInfoConverter;

    private final ApplicantRepository applicantRepository;

    private final MessageSourceService mesService;

    private final NotifyHolderRepository notifyHolderRepository;

    private final PersonRepository personRepository;

    @Autowired
    public InfoServiceImpl(FacultyRepository facultyRepository,
                           PersonInfoService personInfoService,
                           FacultyInfoRepository facultyInfoRepository, FacultyInfoConverter facultyInfoConverter,
                           ApplicantRepository applicantRepository, MessageSourceService mesService,
                           NotifyHolderRepository notifyHolderRepository, PersonRepository personRepository) {
        this.facultyRepository = facultyRepository;
        this.personInfoService = personInfoService;
        this.facultyInfoRepository = facultyInfoRepository;
        this.facultyInfoConverter = facultyInfoConverter;
        this.applicantRepository = applicantRepository;
        this.mesService = mesService;
        this.notifyHolderRepository = notifyHolderRepository;
        this.personRepository = personRepository;
    }


    public List<FacultyInfoDTO> getAllFaculties() {
        List<FacultyInfo> faculties = facultyInfoRepository.findAll();
        if (personInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facultyInfoConverter.toRuFacultyInfoDTO(faculties);
        } else {
            return facultyInfoConverter.toEnFacultyInfoDTO(faculties);
        }
    }

    public FacultyInfoDTO getFacultyInfo(Integer id) {
        FacultyInfo facultyInfo = facultyInfoRepository.findByFaculty_Id(id);
        if (personInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facultyInfoConverter.toRuFacultyInfoDTO(facultyInfo);
        } else {
            return facultyInfoConverter.toEnFacultyInfoDTO(facultyInfo);
        }
    }

    @Transactional
    public void notifyMe(Integer facultyId) {
       NotifyHolder notifyHolder = notifyHolderRepository.getNotifyHolderByFaculty_Id(facultyId).orElse(null);
        if (notifyHolder == null) {
            notifyHolder = new NotifyHolder();
            Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
            if (faculty == null) {
                log.warn("Faculty not found!");
                return;
            }
            notifyHolder.setFaculty(faculty);
        }
        List<String> emails = notifyHolder.getEmails();
        String currentLoggedPersonEmail = personInfoService.getCurrentLoggedPersonEmail();
        if (emails.contains(currentLoggedPersonEmail)) {
            return;
        }
        emails.add(currentLoggedPersonEmail);
        notifyHolderRepository.save(notifyHolder);
        log.info("Person with email ".concat(currentLoggedPersonEmail).concat(" will be notified"));
    }

    public String getWeSendYouEmailMessage() {
        return mesService.getWeSendYouEmailMessage();
    }

    @Override
    public AccountInfoDTO getAccountInfo() {
        Integer id = personInfoService.getCurrentLoggedPersonId();
        Person person = personRepository.findById(id).orElse(null);
        if(person != null) {
            Applicant applicant = applicantRepository.getApplicantWithFacultyById(person.getId());
            return buildAccountInfo(person, applicant);
        }

        return null;
    }

    private AccountInfoDTO buildAccountInfo(Person person,Applicant applicant) {
        AccountInfoDTO account = new AccountInfoDTO();
        account.setFullName(person.getFirstName().concat(" ").concat(person.getLastName()));
        account.setEmail(person.getEmail());
        account.setBirthdate(person.getBirthdate());
        if (applicant != null) {
            Locale currentLoggedPersonLocale = personInfoService.getCurrentLoggedPersonLocale();
            if (currentLoggedPersonLocale.getLanguage().equals("ru")) {
                account.setFaculty(applicant.getFaculty());
                account.setScore(applicant.getScore());
                account.setStatus(applicant.getIsAccepted() ? ApplicationStatus.CONFIRMED : ApplicationStatus.IN_PROCESS);
            }
        }
        return account;
    }

}
