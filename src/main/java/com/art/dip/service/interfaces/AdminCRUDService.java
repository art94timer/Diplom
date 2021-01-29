package com.art.dip.service.interfaces;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.Subject;
import com.art.dip.utility.dto.FacultyDTO;

import java.util.List;

public interface AdminCRUDService {

    void addSubject(Subject subject);
    List<Subject> getSubjects();

    void deleteSubject(Integer subId);

    String getSuccessfullyDeleteSubjectMessage();

    Subject getSubjectWithFaculties(Integer subId);

    String getSuccessfullyEditMessage();

    List<Faculty> getAllFacultiesIsNotAvailable();


    List<Faculty> getFacultiesWithInfo();

    Faculty getFacultyWithInfo(Integer facultyId);

    Faculty createFaculty(Faculty faculty);

    void sendNotificationEmailToApplicants(FacultyInfo faculty);

    void updateAvailableFaculty(FacultyInfo facultyInfo);

    FacultyInfo getFacultyInfo(Integer id);

    Faculty getFacultyWithSubjects(Integer facultyId);

    void deleteSubjectFromFaculty(Integer subjectId,Integer facultyId);

    void addSubjectToFaculty(Integer subjectId, Integer facultyId);

    List<FacultyDTO> getFaculties();

    void openRecruiting(FacultyInfo faculty);

    void disableFaculty(Integer facultyInfo);

    void deleteFaculty(Integer facultyId);

}
