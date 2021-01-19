package com.art.dip.service.interfaces;

import com.art.dip.model.Person;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;

public interface EmailService {
    void sendRegistrationMessage(Person user, OnRegistrationCompleteEvent event, String token);

    void sendValidApplicantEmail(ValidateFormApplicantDTO dto);

    void sendInvalidApplicantEmail(ValidateFormApplicantDTO dto);
}
