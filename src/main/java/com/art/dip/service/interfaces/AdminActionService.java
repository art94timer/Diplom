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



}
