package com.art.dip.service.interfaces;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.Subject;

import java.util.List;
import java.util.Map;

public interface AdminCRUDService {

    void createOrEditSubject(Subject subject);

    List<Subject> getSubjects();

    void deleteSubject(Integer subId);

    String getSuccessfullyDeleteSubjectMessage();

    String getSuccessfullyEditMessage();

    Map<String, List<Faculty>> getFacultiesWithInfo();

    void createOrEditFaculty(Faculty faculty);

    void sendNotificationEmailToApplicants(FacultyInfo faculty);

    void updateAvailableFaculty(FacultyInfo facultyInfo);

    FacultyInfo getFacultyInfo(Integer id);

    Faculty getFacultyWithSubjects(Integer facultyId);

    void deleteSubjectFromFaculty(Integer subjectId, Integer facultyId);

    void addSubjectToFaculty(Integer subjectId, Integer facultyId);

    void openRecruiting(FacultyInfo faculty);

    void disableFaculty(Integer facultyInfo);

    void deleteFaculty(Integer facultyId);

    Subject getSubject(Integer subjectId);
}
