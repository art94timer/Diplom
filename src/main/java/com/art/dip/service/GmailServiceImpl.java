package com.art.dip.service;

import com.art.dip.model.Person;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.dto.CauseInvalid;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;
import com.art.dip.utility.exception.ValidationFormException;
import com.art.dip.utility.localization.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;



@Slf4j
public class GmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    private final MessageSourceService mesService;

    @Autowired
    public GmailServiceImpl(JavaMailSender sender, MessageSourceService mesService) {

        this.sender = sender;
        this.mesService = mesService;
    }

    @Override
    public void sendRegistrationMessage(Person user, OnRegistrationCompleteEvent event, String token) {
        String subject = mesService.getRegistrationConfirmSubjectMessage();
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = mesService.getRegistrationConfirmBodyMessage(new String[]{user.getFirstName(),confirmationUrl});
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);
        sender.send(email);
    }

    @Override
    public void sendValidApplicantEmail(ValidateFormApplicantDTO dto) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(dto.getEmail());
        email.setSubject(mesService.getValidApplicantEmailSubjectMessage());
        email.setText(mesService.getValidApplicantEmailBodyMessage());
        sender.send(email);
    }

    @Override
    public void sendInvalidApplicantEmail(ValidateFormApplicantDTO dto) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(dto.getEmail());
        email.setSubject(mesService.getInvalidApplicantEmailSubjectMessage(mesService.supposedLocale(dto)));
        email.setText(mesService.createInvalidApplicantMessage(dto));
        sender.send(email);
    }




}
