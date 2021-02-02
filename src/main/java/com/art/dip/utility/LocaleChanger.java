package com.art.dip.utility;

import com.art.dip.repository.PersonRepository;
import com.art.dip.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Component
public class LocaleChanger {

    private final PersonRepository repository;

    @Autowired
    public LocaleChanger(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void changeLocale(Locale locale) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            SecurityUser person = (SecurityUser) auth.getPrincipal();
            repository.changeLocale(locale, person.getId());
        }
    }
}
