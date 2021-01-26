package com.art.dip.service;

import com.art.dip.model.*;
import com.art.dip.repository.*;
import com.art.dip.service.interfaces.AdminActionService;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.converter.ApplicantConverter;
import com.art.dip.utility.converter.FacultyConverter;
import com.art.dip.utility.converter.GradeConverter;
import com.art.dip.utility.dto.*;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;
import com.art.dip.utility.exception.FacultyCRUDException;
import com.art.dip.utility.exception.SubjectCRUDException;
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

    private final CurrentPersonInfoService currentPersonInfoService;

    private final ApplicantConverter applicantConverter;

    private final GradeConverter gradeConverter;

    private final SubjectRepository subjectRepository;

    private final FacultyInfoRepository facultyInfoRepository;


    @Autowired
    public AdminActionServiceImpl(ApplicantRepository repository,
                                  CertificateRepository certificateRepository, FacultyRepository facultyRepository,
                                  GradeRepository gradeRepository,
                                  InvalidApplicantRepository invalidApplicantRepository,
                                  EmailService emailService, FacultyConverter facultyConverter,
                                  MessageSourceService mesService,
                                  CurrentPersonInfoService currentPersonInfoService,
                                  ApplicantConverter applicantConverter, GradeConverter gradeConverter, SubjectRepository subjectRepository, FacultyInfoRepository facultyInfoRepository) {
        this.applicantRepository = repository;
        this.certificateRepository = certificateRepository;
        this.facultyRepository = facultyRepository;
        this.gradeRepository = gradeRepository;
        this.invalidApplicantRepository = invalidApplicantRepository;
        this.emailService = emailService;
        this.facultyConverter = facultyConverter;
        this.mesService = mesService;
        this.currentPersonInfoService = currentPersonInfoService;
        this.applicantConverter = applicantConverter;
        this.gradeConverter = gradeConverter;
        this.subjectRepository = subjectRepository;
        this.facultyInfoRepository = facultyInfoRepository;
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
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facultyConverter.toRuFacultyDTO(faculties);
        } else {
            return facultyConverter.toEnFacultyDTO(faculties);
        }
    }

    public List<ValidateApplicantDTO> prepareValidateFormModelAndView(AdminSettings settings) {
        List<Applicant> applicants = applicantRepository.getApplicantsForValidating(settings.getFaculty(), PageRequest.of(0,
                settings.getCountApplicants(), Sort.by(Sort.Direction.ASC, "registrationTime")));

        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return applicantConverter.toRuValidateApplicantDTO(applicants);
        } else {
            return applicantConverter.toEnValidateApplicantDTO(applicants);
        }

    }

    private List<ValidateGradeDTO> getGradesForApplicant(Integer id) {
        List<Grade> grades = gradeRepository.findAllByApplicant_Id(id);
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return gradeConverter.toRuValidateGradeDTO(grades);
        } else {
            return gradeConverter.toEnValidateGradeDTO(grades);
        }
    }


    public List<ValidateApplicantDTO> resolveMistakes(List<ValidateFormApplicantDTO> mistakes) {
        List<Applicant> applicants = mistakes.stream().map(x ->
                applicantRepository.getApplicantForValidatingByEmail(x.getEmail()))
                .collect(Collectors.toList());
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return applicantConverter.toRuValidateApplicantDTO(applicants);
        } else {
            return applicantConverter.toEnValidateApplicantDTO(applicants);
        }
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
    @Transactional
    public void deleteFacultyFromSubject(Integer facultyId, Integer subjectId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null && faculty.getInfo() != null && !faculty.getInfo().isAvailable()) {
            Subject subject = subjectRepository.getSubjectByIdWithFaculties(subjectId);
            List<Faculty> faculties = subject.getFaculties();
            faculties.remove(faculty);
            subjectRepository.save(subject);
        } else {
            throw new FacultyCRUDException(mesService.getFacultyImmutableMessage());
        }
    }

    @Override
    public String getSuccessfullyEditMessage() {
        return mesService.getSuccessfullyEditMessage();
    }


    @Override
    public List<Faculty> getAllFacultiesIsNotAvailable() {
        return facultyRepository.findAllByInfo_IsAvailable(false);
    }

    @Override
    @Transactional
    public void addFacultyToSubject(Integer facultyId, Integer subjectId) {
        Faculty faculty = facultyRepository.findById(facultyId).get();
        Subject subject = subjectRepository.getSubjectByIdWithFaculties(subjectId);
        List<Faculty> faculties = subject.getFaculties();
        if (faculties.contains(faculty)) {
            throw new SubjectCRUDException(mesService.getFacultyInSubjectAlreadyExist());
        }
        faculties.add(faculty);
        subjectRepository.save(subject);
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
    public void createFaculty(Faculty faculty) {

        if (faculty.getId() != null) {
            Faculty facultyInBase = facultyRepository.findById(faculty.getId()).orElse(null);
            if (facultyInBase != null) {
                facultyInBase.setName(faculty.getName());
                facultyInBase.setRuName(faculty.getRuName());
                facultyRepository.save(facultyInBase);
            }
        } else {
            List<Faculty> all = facultyRepository.findAll();
            all.stream().forEach(x -> {
                if ((x.getName().equals(faculty.getName()) || (x.getRuName().equals(faculty.getRuName())))) {
                    throw new FacultyCRUDException(mesService.getFacultyExistMessage());
                }
            });
            facultyRepository.save(faculty);
        }
    }

    @Override
    public void sendNotificationEmailToApplicants(FacultyInfo faculty) {
        List<Applicant> applicants = applicantRepository.getApplicantsByFaculty_Id(faculty.getFaculty().getId());
       applicants.forEach(x->emailService.sendCheckChangeEmail(x.getPerson().getEmail()));
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
        return facultyRepository.findFacultyByIdWithSubject(facultyId);
    }

    @Override
    @Transactional
    public void deleteSubjectFromFaculty(Integer subjectId,Integer facultyId) {
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
                subject.getFaculties().add(faculty);
                subjectRepository.save(subject);
                facultyRepository.save(faculty);
            }
        }
    }
}

