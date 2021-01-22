package com.art.dip.service.interfaces;

import com.art.dip.utility.dto.ApplicantDTO;
import com.art.dip.utility.dto.FacultyDTO;

import java.util.List;

public interface ApplicantService {

    List<FacultyDTO> getFaculties();

    FacultyDTO getFacultyWithSubjects(Integer id);

    void save(ApplicantDTO app);

    ApplicantDTO prepareApplicantDTO(ApplicantDTO applicantDTO);


}
