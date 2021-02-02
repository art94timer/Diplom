package com.art.dip.service;

import com.art.dip.model.Faculty;
import com.art.dip.model.Person;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("!gun")
@Component
public class GmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    private final MessageSourceService mesService;

    private final PersonInfoService personInfoService;

    @Autowired
    public GmailServiceImpl(JavaMailSender sender, MessageSourceService mesService, PersonInfoService personInfoService) {

        this.sender = sender;
        this.mesService = mesService;
        this.personInfoService = personInfoService;
    }

    private SimpleMailMessage prepareEmail(String email,String subject,String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(email);
        return simpleMailMessage;
    }

    @Override
    public void sendRegistrationMessage(Person user,  String token,String appUrl) {
        String subject = mesService.getRegistrationConfirmSubjectMessage(user.getLocale());
        String confirmationUrl
                = appUrl + "/registrationConfirm?token=" + token;
        String message = mesService.getRegistrationConfirmBodyMessage(new String[]{user.getFirstName(),confirmationUrl},user.getLocale());
        sender.send(prepareEmail(user.getEmail(),subject,message));
    }

    @Override
    public void sendValidApplicantEmail(ValidateFormApplicantDTO dto) {
        Locale personLocale = personInfoService.getPersonLocale(dto.getEmail());
        String subject = mesService.getValidApplicantEmailSubjectMessage(personLocale);
        String body = mesService.getValidApplicantEmailBodyMessage(personLocale);
        sender.send(prepareEmail(dto.getEmail(),subject,body));
    }

    @Override
    public void sendInvalidApplicantEmail(ValidateFormApplicantDTO dto) {
        String to = dto.getEmail();
        String subject = mesService.getInvalidApplicantEmailSubjectMessage(to);
        String body = mesService.createInvalidApplicantMessage(dto);
        sender.send(prepareEmail(to,subject,body));
    }

    @Override
    public void sendCheckChangeEmail(String to) {
        Locale personLocale = personInfoService.getPersonLocale(to);
        String subject = mesService.getFacultyIsChangedSubjectMessage(personLocale);
        String body = mesService.getFacultyIsChangedBodyMessage(personLocale);
        sender.send(prepareEmail(to,subject,body));
    }

    @Override
    public void sendNotifyFacultyAvailableEmail(String to, Faculty faculty) {
        Locale personLocale = personInfoService.getPersonLocale(to);
        String subject = mesService.getNotifyFacultyAvailableSubjectMessage(personLocale);
        String body = mesService.getNotifyFacultyAvailableBodyMessage(personLocale,faculty);
        sender.send(prepareEmail(to,subject,body));
    }

    @Override
    public void sendWeSorryFacultyIsDisabled(String email, Faculty faculty) {
        Locale personLocale = personInfoService.getPersonLocale(email);
        String subject = mesService.getWeSorryFacultyDisabledSubjectMessage(personLocale);
        String body = mesService.getWeSorryFacultyDisabledBodyMessage(personLocale,faculty);
        sender.send(prepareEmail(email,subject,body));
    }

    @Override
    public void sendCongratulationEmail(Faculty faculty, String email) {
        Locale personLocale = personInfoService.getPersonLocale(email);
        String subject = mesService.getCongratulationEmailSubjectMessage(personLocale);
        String body = mesService.getCongratulationEmailBodyMessage(personLocale,faculty);
        sender.send(prepareEmail(email,subject,body));
    }

    @Override
    public void sendMaybeNextTimeEmail(Faculty faculty, String email) {
        Locale personLocale = personInfoService.getPersonLocale(email);
        String subject = mesService.getMaybeNextTimeEmailSubjectMessage(personLocale);
        String body = mesService.getMaybeNextTimeEmailBodyMessage(personLocale,faculty);
        sender.send(prepareEmail(email,subject,body));
    }


}
