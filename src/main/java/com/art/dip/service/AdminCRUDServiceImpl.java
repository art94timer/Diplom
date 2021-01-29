package com.art.dip.service;

import com.art.dip.model.*;
import com.art.dip.repository.*;
import com.art.dip.service.interfaces.AdminCRUDService;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.exception.FacultyCRUDException;
import com.art.dip.utility.exception.SubjectCRUDException;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminCRUDServiceImpl implements AdminCRUDService {
    private final FacultyRepository facultyRepository;

    private final SubjectRepository subjectRepository;

    private final MessageSourceService mesService;

    private final ApplicantRepository applicantRepository;

    private final EmailService emailService;

    private final FacultyInfoRepository facultyInfoRepository;

    private final NotifyHolderRepository notifyHolderRepository;

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
    public void addSubject(Subject subject) {
        if (subject != null) {
            if (subject.getFaculties() != null) {
                subject.getFaculties().forEach(x -> facultyRepository.findById(x.getId()));
            }
            subjectRepository.save(subject);
        }
    }

    @Override
    public List<Subject> getSubjects() {

        return subjectRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteSubject(Integer subId) throws SubjectCRUDException {
        Subject subject = subjectRepository.findById(subId).get();
        if (!subject.getFaculties().isEmpty()) {
            throw new SubjectCRUDException(mesService.getSubjectBelongToFacultyMessage());
        }
        subjectRepository.deleteById(subId);

    }

    @Override
    public String getSuccessfullyDeleteSubjectMessage() {
        return mesService.getSuccessfullyDeleteSubjectMessage();
    }

    @Override
    public Subject getSubjectWithFaculties(Integer subId) {
        Subject subject = subjectRepository.getSubjectsWithFaculties(subId);
        return subject;
    }


    @Override
    public String getSuccessfullyEditMessage() {
        return mesService.getSuccessfullyEditMessage();
    }


    @Override
    public List<Faculty> getAllFacultiesIsNotAvailable() {
        return facultyRepository.findAllByInfo_IsAvailable(false);
    }


    public List<Faculty> getFacultiesWithInfo() {

        return facultyRepository.findAllWithInfo();
    }

    @Override
    public Faculty getFacultyWithInfo(Integer facultyId) {
         return facultyRepository.findFacultyByIdWithInfo(facultyId);
    }

    @Override
    @Transactional
    public Faculty createFaculty(Faculty faculty) {
        if (faculty.getId() != null) {
            Faculty facultyInBase = facultyRepository.findById(faculty.getId()).orElse(null);
            if (facultyInBase != null) {
                facultyInBase.setName(faculty.getName());
                facultyInBase.setRuName(faculty.getRuName());
                facultyRepository.save(facultyInBase);
                return facultyInBase;
            }
        } else {
            List<Faculty> all = facultyRepository.findAll();
            all.forEach(x -> {
                if ((x.getName().equals(faculty.getName()) || (x.getRuName().equals(faculty.getRuName())))) {
                    throw new FacultyCRUDException(mesService.getFacultyExistMessage());
                }
            });
            faculty.setSubjects(new ArrayList<>());
            Faculty saved = facultyRepository.save(faculty);
            prepareFacultyInfo(saved);

            prepareFacultyNotifyHolder(saved);
        }

        return faculty;
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
        Faculty faculty = facultyRepository.findFacultyByIdWithSubject(facultyId);
        System.out.println();
        return faculty;
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
            }
        }
    }

    @Override
    public List<FacultyDTO> getFaculties() {
        return null;
    }

    @Override
    @Transactional
    public void openRecruiting(FacultyInfo faculty) {
        Faculty f = facultyRepository.findById(faculty.getFaculty().getId()).orElse(null);
        if (f != null && f.getSubjects() != null && !f.getSubjects().isEmpty()) {
            NotifyHolder notifyHolder = notifyHolderRepository.getNotifyHolderByFaculty_Id(f.getId()).orElse(null);
            FacultyInfo facultyInfo = facultyInfoRepository.findByFaculty_Id(f.getId());
            facultyInfo.setAvailable(true);
            facultyInfo.setCapacity(faculty.getCapacity());
            facultyInfo.setCountApplicants(0);
            facultyInfo.setAverageScore(0d);
            facultyInfo.setExpiredDate(faculty.getExpiredDate());
            f.setInfo(facultyInfo);
            facultyRepository.save(f);
            if (notifyHolder != null) {
                notifyHolder.getEmails().forEach(x -> {
                    emailService.sendNotifyFacultyAvailableEmail(x, f);
                });
                notifyHolder.getEmails().clear();
                notifyHolderRepository.save(notifyHolder);
            }
        }
    }


    @Override
    @Transactional
    public void disableFaculty(Integer faculty) {
        FacultyInfo info = facultyInfoRepository.findByFaculty_Id(faculty);
        info.setAvailable(false);
        facultyInfoRepository.save(info);
    }

    @Override
    public void deleteFaculty(Integer facultyId) {
        Optional<Faculty> byId = facultyRepository.findById(facultyId);
        byId.ifPresent(facultyRepository::delete);
    }
}
