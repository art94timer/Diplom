package com.art.dip.service.interfaces;

import com.art.dip.utility.dto.AccountInfoDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;

import java.util.List;

public interface InfoService {

    List<FacultyInfoDTO> getAllFaculties();

    FacultyInfoDTO getFacultyInfo(Integer id);

    void notifyMe(Integer facultyId);

    String getWeSendYouEmailMessage();

    AccountInfoDTO getAccountInfo();
}
