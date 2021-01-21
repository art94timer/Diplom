package com.art.dip.service;

import com.art.dip.model.Person;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;
import com.art.dip.utility.localization.MessageSourceService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Component;

@Component
public class MailGunServiceImpl implements EmailService {

    private final MailGunClient client;

    private final MessageSourceService mesService;

    public MailGunServiceImpl(MailGunClient client, MessageSourceService mesService) {
        this.client = client;
        this.mesService = mesService;
    }

    @Override
    public void sendRegistrationMessage(Person user, OnRegistrationCompleteEvent event, String token) {
        try {
            String confirmationUrl
                    = event.getAppUrl() + "/registrationConfirm?token=" + token;
            String subject = mesService.getRegistrationConfirmSubjectMessage();
            String message = mesService.getRegistrationConfirmBodyMessage(new String[]{user.getFirstName(), confirmationUrl});
            client.sendText(user.getEmail(), subject, message);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendValidApplicantEmail(ValidateFormApplicantDTO dto) {
        try {
            client.sendText(dto.getEmail(), mesService.getValidApplicantEmailSubjectMessage()
                    , mesService.getValidApplicantEmailBodyMessage());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendInvalidApplicantEmail(ValidateFormApplicantDTO dto) {
        try {
            String subject = mesService.getInvalidApplicantEmailSubjectMessage(mesService.supposedLocale(dto));
            String body = mesService.createInvalidApplicantMessage(dto);
            client.sendText(dto.getEmail(), subject, body);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
