package com.art.dip.utility.listener;

import com.art.dip.model.Person;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.service.interfaces.PersonService;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConfirmationListener implements ApplicationListener<OnRegistrationCompleteEvent> {


    private final PersonService service;
    @Qualifier(value = "gun")
    private final EmailService emailService;


    private final MessageSourceService mesService;

    @Autowired
    public ConfirmationListener(PersonService service, EmailService emailService,  MessageSourceService mesService) {
        this.service = service;
        this.emailService = emailService;
        this.mesService = mesService;
    }

    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Person user = event.getPerson();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        String subject = mesService.getRegistrationConfirmSubjectMessage();
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = mesService.getRegistrationConfirmBodyMessage(new String[]{user.getFirstName(),confirmationUrl});
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);
       emailService.sendRegistrationMessage(user,event,token);


    }
}

