package com.art.dip.service.interfaces;

import com.art.dip.model.Faculty;
import com.art.dip.model.Person;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;

public interface EmailService {

    void sendRegistrationMessage(Person user, String token,String appUrl);

    void sendValidApplicantEmail(ValidateFormApplicantDTO dto);

    void sendInvalidApplicantEmail(ValidateFormApplicantDTO dto);

    void sendCheckChangeEmail(String email);

    void sendNotifyFacultyAvailableEmail(String email, Faculty faculty);

    void sendWeSorryFacultyIsDisabled(String email,Faculty faculty);

    void sendCongratulationEmail(Faculty faculty,String email);

    void sendMaybeNextTimeEmail(Faculty faculty, String email);
}
