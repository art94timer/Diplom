package com.art.dip.service;

import com.art.dip.model.*;
import com.art.dip.repository.*;
import com.art.dip.service.interfaces.AdminCRUDService;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.exception.FacultyCRUDException;
import com.art.dip.utility.exception.SubjectCRUDException;
import com.art.dip.utility.localization.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class AdminCRUDServiceImpl implements AdminCRUDService {

    private final FacultyRepository facultyRepository;

    private final SubjectRepository subjectRepository;

    private final MessageSourceService mesService;

    private final ApplicantRepository applicantRepository;

    private final EmailService emailService;

    private final FacultyInfoRepository facultyInfoRepository;

    private final NotifyHolderRepository notifyHolderRepository;

    @Autowired
    public AdminCRUDServiceImpl(FacultyRepository facultyRepository, SubjectRepository subjectRepository, MessageSourceService mesService, ApplicantRepository applicantRepository, EmailService emailService, FacultyInfoRepository facultyInfoRepository, NotifyHolderRepository notifyHolderRepository) {
        this.facultyRepository = facultyRepository;
        this.subjectRepository = subjectRepository;
        this.mesService = mesService;
        this.applicantRepository = applicantRepository;
        this.emailService = emailService;
        this.facultyInfoRepository = facultyInfoRepository;
        this.notifyHolderRepository = notifyHolderRepository;
    }

    @Override
    @Transactional
    public void createOrEditSubject(Subject subject) {
        if (subject != null) {
            //edit
            if (subject.getId() != null) {
                Subject subInBase = subjectRepository.findById(subject.getId()).orElse(null);
                if (subInBase != null) {
                    if (subject.getName() != null) {
                        subInBase.setName(subject.getName());
                    }
                    if(subject.getRuName() != null) {
                        subInBase.setRuName(subject.getRuName());
                    }
                    subjectRepository.save(subInBase);
                    log.warn("Subject ".concat(subject.getName()).concat("was edited"));
                }
            }

            //create
            if (subject.getId() == null) {
                String name = subject.getName();
                String ruName= subject.getRuName();
                if (name == null || ruName == null || name.isEmpty() || ruName.isEmpty()) {
                    throw new SubjectCRUDException(mesService.getInvalidNameMessage());
                }
                List<Subject> all = subjectRepository.findAll();
                all.forEach(x->{
                    if (x.getName().equalsIgnoreCase(name) || x.getRuName().equalsIgnoreCase(ruName)) {
                        log.warn("Attempt to create duplicate subject");
                        throw new SubjectCRUDException(mesService.getSubjectIsExistMessage());
                    }
                });
            }
            subjectRepository.save(subject);
            log.info("Subject with id - ".concat(String.valueOf(subject.getId())).concat(" was created"));
        }
    }

    @Override
    public List<Subject> getSubjects() {

        return subjectRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteSubject(Integer subId) throws SubjectCRUDException {
        Optional<Subject> subject = subjectRepository.findById(subId);
        subject.ifPresent(x-> {
            if (!x.getFaculties().isEmpty()) {
                log.warn("Attempt to delete subject with not empty faculties");
                throw new SubjectCRUDException(mesService.getSubjectBelongToFacultyMessage());
            }
            subjectRepository.deleteById(subId);
            log.warn("Subject with id - ".concat(String.valueOf(subId).concat("was deleted")));
        });
    }

    @Override
    public String getSuccessfullyDeleteSubjectMessage() {
        return mesService.getSuccessfullyDeleteSubjectMessage();
    }


    @Override
    public String getSuccessfullyEditMessage() {
        return mesService.getSuccessfullyEditMessage();
    }


    public Map<String,List<Faculty>> getFacultiesWithInfo() {
        Map<String,List<Faculty>> map = new HashMap<>();
        List<Faculty> allWithInfo = facultyRepository.findAllWithInfo();
        map.put("available",new ArrayList<>());
        map.put("notAvailable",new ArrayList<>());
        allWithInfo.forEach(x->{
            if (x.getInfo().isAvailable()) {
                map.get("available").add(x);
            } else {
                map.get("notAvailable").add(x);
            }
        });
        return map;
    }


    @Override
    @Transactional
    public void createOrEditFaculty(Faculty faculty) {
        if (faculty != null && faculty.getName() != null &&
                faculty.getRuName() != null && !faculty.getRuName().isEmpty() && !faculty.getName().isEmpty()) {
            if (faculty.getId() != null) {
                Faculty facultyInBase = facultyRepository.findById(faculty.getId()).orElse(null);
                if (facultyInBase != null) {
                    facultyInBase.setName(faculty.getName());
                    facultyInBase.setRuName(faculty.getRuName());
                    facultyRepository.save(facultyInBase);
                    log.info("Faculty with name ".concat(facultyInBase.getName()).concat(" was edited"));

                }
            } else {
                List<Faculty> all = facultyRepository.findAll();
                all.forEach(x -> {
                    if ((x.getName().equals(faculty.getName()) || (x.getRuName().equals(faculty.getRuName())))) {
                        log.warn("Attempt to create duplicate faculty");
                        throw new FacultyCRUDException(mesService.getFacultyExistMessage());
                    }
                });
                faculty.setSubjects(new ArrayList<>());
                Faculty saved = facultyRepository.save(faculty);
                prepareFacultyInfo(saved);
                prepareFacultyNotifyHolder(saved);
            }

        } else {
            log.warn("Invalid name(null or isEmpty)");
            throw new FacultyCRUDException(mesService.getInvalidNameMessage());
        }


    }

    private void prepareFacultyNotifyHolder(Faculty faculty) {
        NotifyHolder notifyHolder = new NotifyHolder();
        notifyHolder.setFaculty(faculty);
        notifyHolderRepository.save(notifyHolder);
    }

    private void prepareFacultyInfo(Faculty faculty) {
        FacultyInfo facultyInfo = new FacultyInfo();
        facultyInfo.setCountApplicants(0);
        facultyInfo.setAverageScore(0d);
        facultyInfo.setAvailable(false);
        facultyInfo.setCapacity(0);
        facultyInfo.setFaculty(faculty);
        faculty.setInfo(facultyInfo);
    }


    @Override
    public void sendNotificationEmailToApplicants(FacultyInfo faculty) {
        List<Applicant> applicants = applicantRepository.getApplicantsByFaculty_Id(faculty.getFaculty().getId());
        applicants.forEach(x -> emailService.sendCheckChangeEmail(x.getPerson().getEmail()));
    }

    @Override
    @Transactional
    public void updateAvailableFaculty(FacultyInfo facultyInfo) {
        Faculty facultyInBase = facultyRepository.findById(facultyInfo.getFaculty().getId()).orElse(null);
        if (facultyInBase != null && facultyInBase.getInfo() != null) {
            boolean isEquals = true;
            if (!facultyInBase.getInfo().getExpiredDate().isEqual(facultyInfo.getExpiredDate()) ||
                    !facultyInBase.getInfo().getCapacity().equals(facultyInfo.getCapacity())) {
                isEquals = false;
            }
            if (!isEquals) {
                facultyInBase.getInfo().setCapacity(facultyInfo.getCapacity());
                facultyInBase.getInfo().setExpiredDate(facultyInfo.getExpiredDate());
                facultyRepository.save(facultyInBase);
                log.warn("Updated ".concat(facultyInBase.getName().concat("with available recruitment!")));
                sendNotificationEmailToApplicants(facultyInfo);
            }
        }
    }

    @Override
    public FacultyInfo getFacultyInfo(Integer id) {
        return facultyInfoRepository.findByFaculty_Id(id);
    }

    @Override
    public Faculty getFacultyWithSubjects(Integer facultyId) {
        return facultyRepository.findFacultyByIdWithSubject(facultyId);
    }

    @Override
    @Transactional
    public void deleteSubjectFromFaculty(Integer subjectId, Integer facultyId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject != null) {
            Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
            if (faculty != null) {
                faculty.getSubjects().remove(subject);
                subject.getFaculties().remove(faculty);
                facultyRepository.save(faculty);
                subjectRepository.save(subject);
                log.warn("Subject ".concat(subject.getName().concat(" deleted from ").concat(faculty.getName())));
            }
        }
    }

    @Override
    @Transactional
    public void addSubjectToFaculty(Integer subjectId, Integer facultyId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject != null) {
            Faculty faculty = facultyRepository.findFacultyByIdWithSubject(facultyId);
            if (faculty != null && faculty.getSubjects() != null && !faculty.getSubjects().contains(subject)) {
                faculty.getSubjects().add(subject);
                facultyRepository.save(faculty);
                log.warn("Subject ".concat(subject.getName()).concat(" was added to ").concat(faculty.getName()));

            }
        }
    }


    @Override
    @Transactional
    public void openRecruiting(FacultyInfo facultyInfo) {
        if (facultyInfo != null && facultyInfo.getExpiredDate() != null
                && facultyInfo.getExpiredDate().isAfter(LocalDateTime.now()) && facultyInfo.getCapacity() != null && facultyInfo.getCapacity() > 0  ) {
            Faculty faculty = facultyRepository.findById(facultyInfo.getFaculty().getId()).orElse(null);
            if (faculty != null && faculty.getSubjects() != null && !faculty.getSubjects().isEmpty()) {
                prepareFacultyInfoForOpen(faculty, facultyInfo);
                facultyRepository.save(faculty);
                notifyPersons(faculty);
                log.info(faculty.getName().concat(" open recruitment!"));
            } else {
                log.warn("Attempt to open recruitment with empty subjects");
                throw new FacultyCRUDException(mesService.getSubjectsNotFoundMessage());
            }
        }else {
            log.warn("Attempt to open recruitment with invalid date or capacity");
            throw new FacultyCRUDException(mesService.getInvalidCapacityOrInvalidDate());
        }
    }

    private void prepareFacultyInfoForOpen(Faculty faculty,FacultyInfo facultyInfo) {
        FacultyInfo facultyInfoInBase = facultyInfoRepository.findByFaculty_Id(faculty.getId());
        facultyInfoInBase.setAvailable(true);
        facultyInfoInBase.setCapacity(facultyInfo.getCapacity());
        facultyInfoInBase.setCountApplicants(0);
        facultyInfoInBase.setAverageScore(0d);
        facultyInfoInBase.setExpiredDate(facultyInfo.getExpiredDate());
        facultyInfoInBase.setUpdateTime(LocalDateTime.now());
        faculty.setInfo(facultyInfoInBase);
    }

    private void notifyPersons(Faculty faculty) {
        NotifyHolder notifyHolder = notifyHolderRepository.getNotifyHolderByFaculty_Id(faculty.getId()).orElse(null);
        if (notifyHolder != null) {
            notifyHolder.getEmails().forEach(x -> emailService.sendNotifyFacultyAvailableEmail(x, faculty));
            notifyHolder.getEmails().clear();
            notifyHolderRepository.save(notifyHolder);
            log.info("All User was notified!");
        }
    }

    @Override
    @Transactional
    public void disableFaculty(Integer facultyId) {
        Faculty facultyWithInfo = facultyRepository.findFacultyByIdWithInfo(facultyId);
        facultyWithInfo.getInfo().setAvailable(false);
        notifyPersonsDisableFaculty(facultyWithInfo);
        facultyRepository.save(facultyWithInfo);
        log.warn("Recruitment of ".concat(facultyWithInfo.getName()).concat(" was cancelled!"));
    }

    private void notifyPersonsDisableFaculty(Faculty faculty) {
        List<Applicant> applicants = applicantRepository.getApplicantsByFaculty_Id(faculty.getId());
        applicants.forEach(x-> emailService.sendWeSorryFacultyIsDisabled(x.getPerson().getEmail(),faculty));
        applicantRepository.deleteAll(applicants);
        log.info("All Users notified!");
    }

    @Override
    public void deleteFaculty(Integer facultyId) {
        Optional<Faculty> byId = facultyRepository.findById(facultyId);
        byId.ifPresent(facultyRepository::delete);
        byId.ifPresent((x)->log.warn(x.getName().concat(" was deleted!")));
    }

    @Override
    public Subject getSubject(Integer subjectId) {
        return subjectRepository.findById(subjectId).orElse(null);
    }
}
