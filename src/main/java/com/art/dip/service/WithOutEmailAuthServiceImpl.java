package com.art.dip.service;

import com.art.dip.model.Person;
import com.art.dip.model.Role;
import com.art.dip.model.VerifyToken;
import com.art.dip.repository.PersonRepository;
import com.art.dip.service.interfaces.AuthService;
import com.art.dip.utility.converter.PersonConverter;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.exception.PersonAlreadyExistException;
import com.art.dip.utility.localization.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Profile("gun")
@Service
@Slf4j
public class WithOutEmailAuthServiceImpl implements AuthService {


    private final PersonInfoService personInfoService;

    private final MessageSourceService mesService;

    private final PersonConverter converter;

    private final PersonRepository repository;

    @Autowired
    public WithOutEmailAuthServiceImpl(PersonInfoService personInfoService, MessageSourceService mesService, PersonConverter converter, PersonRepository repository) {
        this.personInfoService = personInfoService;
        this.mesService = mesService;
        this.converter = converter;
        this.repository = repository;
    }

    @Override
    public void registerNewPersonAccount(PersonDTO person) throws PersonAlreadyExistException {
        Locale locale = personInfoService.getCurrentLoggedPersonLocale();
        if (emailExist(person.getEmail())) {
            String message = mesService.getEmailIsExistMessage(locale,person.getEmail());
            log.info("Email ".concat(person.getEmail()).concat("is already existed"));
            throw new PersonAlreadyExistException(message, person);
        }
        Person converted = converter.toEntity(person);
        converted.setRole(Role.USER);
        converted.setLocale(locale);
        converted.setEnabled(true);
        Person saved = repository.save(converted);
        log.info("Person was created with email ".concat(saved.getEmail()));
    }

    @Override
    public VerifyToken getVerifyToken(String VerifyToken) {
        throw new UnsupportedOperationException();

    }
    private boolean emailExist(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public String tokenIsExpiredMessage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void verifyPerson(VerifyToken verificationToken) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getVerifyYourEmailMessage() {
        throw new UnsupportedOperationException();
    }
}

