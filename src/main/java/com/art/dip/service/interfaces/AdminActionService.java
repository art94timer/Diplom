package com.art.dip.service.interfaces;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.Subject;
import com.art.dip.utility.dto.*;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;
import com.art.dip.utility.exception.SubjectCRUDException;

import java.util.List;

public interface AdminActionService {

     void handleListForms(ListValidateFormApplicantDTO list) throws AdminMistakeApplicantFormException;

    List<FacultyDTO> getFaculties();

    List<ValidateApplicantDTO> prepareValidateFormModelAndView(AdminSettings settings);

    List<ValidateApplicantDTO> resolveMistakes(List<ValidateFormApplicantDTO> mistakes);


    void addSubject(Subject subject);

    List<Subject> getSubjects();

    void deleteSubject(Integer subId) throws SubjectCRUDException;

    String getSuccessfullyDeleteSubjectMessage();

    Subject getSubjectWithFaculties(Integer subId);

    void deleteFacultyFromSubject(Integer facultyId, Integer subjectId);

    String getSuccessfullyEditMessage();

    List<Faculty> getAllFacultiesIsNotAvailable();

    void addFacultyToSubject(Integer facultyId, Integer subjectId);

    List<Faculty> getFacultiesWithInfo();

    Faculty getFacultyWithInfo(Integer facultyId);

    void createFaculty(Faculty faculty);


    void sendNotificationEmailToApplicants(FacultyInfo faculty);

    void updateAvailableFaculty(FacultyInfo faculty);

    FacultyInfo getFacultyInfo(Integer id);

    Faculty getFacultyWithSubjects(Integer facultyId);

    void deleteSubjectFromFaculty(Integer subjectId,Integer facultyId);

    void addSubjectToFaculty(Integer subjectId, Integer facultyId);

}
