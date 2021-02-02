package com.art.dip.service.interfaces;

import com.art.dip.utility.dto.*;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;

import java.util.List;

public interface AdminActionService {

     void handleListForms(ListValidateFormApplicantDTO list) throws AdminMistakeApplicantFormException;

    List<FacultyDTO> getFaculties();

    List<ValidateApplicantDTO> prepareValidateFormList(AdminSettings settings);

    List<ValidateApplicantDTO> resolveMistakes(List<ValidateFormApplicantDTO> mistakes);




}
