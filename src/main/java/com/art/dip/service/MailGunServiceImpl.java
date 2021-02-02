package com.art.dip.service;

import com.art.dip.model.Faculty;
import com.art.dip.model.Person;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.client.MailGunClient;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.localization.MessageSourceService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("gun")
@Primary
@Component
public class MailGunServiceImpl implements EmailService {

    private final MailGunClient client;

    private final MessageSourceService mesService;

    private final PersonInfoService personInfoService;


    @Autowired
    public MailGunServiceImpl(MailGunClient client, MessageSourceService mesService, PersonInfoService personInfoService) {
        this.client = client;
        this.mesService = mesService;
        this.personInfoService = personInfoService;
    }

    @Override
    public void sendRegistrationMessage(Person user, String token, String appUrl) {
        try {
            String confirmationUrl
                    = appUrl + "/registrationConfirm?token=" + token;
            String subject = mesService.getRegistrationConfirmSubjectMessage(LocaleContextHolder.getLocale());
            String message = mesService.getRegistrationConfirmBodyMessage(new String[]{user.getFirstName(), confirmationUrl}, LocaleContextHolder.getLocale());
            client.sendText(user.getEmail(), subject, message);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendValidApplicantEmail(ValidateFormApplicantDTO dto) {
        try {
            Locale personLocale = personInfoService.getPersonLocale(dto.getEmail());
            client.sendText(dto.getEmail(), mesService.getValidApplicantEmailSubjectMessage(personLocale)
                    , mesService.getValidApplicantEmailBodyMessage(personLocale));
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendInvalidApplicantEmail(ValidateFormApplicantDTO dto) {
        try {
            String subject = mesService.getInvalidApplicantEmailSubjectMessage(dto.getEmail());
            String body = mesService.createInvalidApplicantMessage(dto);
            client.sendText(dto.getEmail(), subject, body);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCheckChangeEmail(String email) {
        try {
            Locale personLocale = personInfoService.getPersonLocale(email);
            String subject = mesService.getFacultyIsChangedSubjectMessage(personLocale);
            String body = mesService.getFacultyIsChangedBodyMessage(personLocale);
            client.sendText(email, subject, body);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendNotifyFacultyAvailableEmail(String email, Faculty faculty) {
        try {
            Locale personLocale = personInfoService.getPersonLocale(email);
            String subject = mesService.getNotifyFacultyAvailableSubjectMessage(personLocale);
            String body = mesService.getNotifyFacultyAvailableBodyMessage(personLocale, faculty);
            client.sendText(email, subject, body);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendWeSorryFacultyIsDisabled(String email, Faculty faculty) {
        try {
            Locale personLocale = personInfoService.getPersonLocale(email);
            String subject = mesService.getWeSorryFacultyDisabledSubjectMessage(personLocale);
            String body = mesService.getWeSorryFacultyDisabledBodyMessage(personLocale, faculty);
            client.sendText(email, subject, body);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCongratulationEmail(Faculty faculty, String email) {
        try {
            Locale personLocale = personInfoService.getPersonLocale(email);
            String subject = mesService.getCongratulationEmailSubjectMessage(personLocale);
            String body = mesService.getCongratulationEmailBodyMessage(personLocale, faculty);
            client.sendText(email, subject, body);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMaybeNextTimeEmail(Faculty faculty, String email) {
        try {
            Locale personLocale = personInfoService.getPersonLocale(email);
            String subject = mesService.getMaybeNextTimeEmailSubjectMessage(personLocale);
            String body = mesService.getMaybeNextTimeEmailBodyMessage(personLocale, faculty);
            client.sendText(email, subject, body);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}
