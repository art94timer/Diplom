package com.art.dip.service.interfaces;

import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;

import java.util.List;

public interface ViewService {

    List<FacultyDTO> getAllFaculties();

    FacultyInfoDTO getFacultyInfo(Integer id);
}
