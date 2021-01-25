package com.art.dip.service;

import com.art.dip.model.Person;
import com.art.dip.repository.PersonRepository;
import com.art.dip.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class CurrentPersonInfoService {

    private final PersonRepository personRepository;

    @Autowired
    public CurrentPersonInfoService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public Integer getCurrentLoggedPersonId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser secUser = (SecurityUser) auth.getPrincipal();
        return secUser.getId();

    }

    public Locale getCurrentLoggedPersonLocale() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
                if (securityUser.getLocale() == null) {
                    return Locale.ENGLISH;
                } else {
                    return securityUser.getLocale();
                }
            }

        return LocaleContextHolder.getLocale();

    }

    public String getCurrentLoggedPersonEmail() {
        Person person = personRepository.findById(getCurrentLoggedPersonId()).get();
        return person.getEmail();
    }
}


