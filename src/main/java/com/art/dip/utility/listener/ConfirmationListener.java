package com.art.dip.utility.listener;

import com.art.dip.model.Person;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.service.interfaces.PersonService;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConfirmationListener implements ApplicationListener<OnRegistrationCompleteEvent> {


    private final PersonService service;

    private final EmailService emailService;

    @Autowired
    public ConfirmationListener(PersonService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Person user = event.getPerson();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        emailService.sendRegistrationMessage(user,event,token);
    }
}

