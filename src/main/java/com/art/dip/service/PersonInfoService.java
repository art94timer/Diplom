package com.art.dip.service;

import com.art.dip.model.Person;
import com.art.dip.repository.PersonRepository;
import com.art.dip.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class PersonInfoService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonInfoService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public Integer getCurrentLoggedPersonId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser secUser = (SecurityUser) auth.getPrincipal();
        return secUser.getId();

    }

    public Locale getCurrentLoggedPersonLocale() {
        return LocaleContextHolder.getLocale();

    }

    public String getCurrentLoggedPersonEmail() {

        Person person = personRepository.findById(getCurrentLoggedPersonId()).orElse(null);
        if (person == null) {
            log.warn("Person not found!");
            return "";
        }
        return person.getEmail();
    }

    public Locale getPersonLocale(String email) {
        Person person = personRepository.findByEmail(email).orElse(null);
        if (person != null) {
            return person.getLocale();
        }
        return Locale.ENGLISH;
    }
}


