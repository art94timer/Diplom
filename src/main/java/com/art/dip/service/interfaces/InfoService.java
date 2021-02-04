package com.art.dip.service.interfaces;

import com.art.dip.utility.dto.AccountInfoDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;

import java.util.List;
import java.util.TimeZone;

public interface InfoService {

    List<FacultyInfoDTO> getAllFaculties();

    FacultyInfoDTO getFacultyInfo(Integer id, TimeZone timeZone);

    void notifyMe(Integer facultyId);

    String getWeSendYouEmailMessage();

    AccountInfoDTO getAccountInfo();
}
