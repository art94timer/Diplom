package com.art.dip.service;


import com.art.dip.model.Person;
import com.art.dip.model.Role;
import com.art.dip.model.VerifyToken;
import com.art.dip.repository.PersonRepository;
import com.art.dip.repository.VerifyTokenRepository;
import com.art.dip.service.interfaces.AuthService;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.converter.PersonConverter;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.exception.PersonAlreadyExistException;
import com.art.dip.utility.localization.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final PersonRepository repository;

	private final VerifyTokenRepository tokRepository;

	private final MessageSourceService mesService;

	private final PersonConverter converter;

	private final EmailService emailService;

	private final PersonInfoService personInfoService;

	@Autowired
	public AuthServiceImpl(
            PersonRepository repository, VerifyTokenRepository tokRepository,
            MessageSourceService mesService, PersonConverter converter, EmailService emailService, PersonInfoService personInfoService) {
		this.repository = repository;
		this.tokRepository = tokRepository;
		this.mesService = mesService;
		this.converter = converter;
		this.emailService = emailService;
		this.personInfoService = personInfoService;
	}

	public Optional<Person> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public String tokenIsExpiredMessage() {
		return mesService.getExpiredTokenMessage();
	}

	@Override
	@Transactional
	public void verifyPerson(VerifyToken token) {
		Person person = token.getPerson();
		person.setEnabled(true);
		repository.save(person);
		log.info("Person was confirmed his email ".concat(person.getEmail()));
	}

	@Override
	public String getVerifyYourEmailMessage() {
		return mesService.getVerifyYourEmailMessage();
	}


	@Override
	@Transactional
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
		Person saved = repository.save(converted);
		handleRegistration(saved);
		log.info("Person was created with email ".concat(person.getEmail()));
	}

	private boolean emailExist(String email) {
		return repository.findByEmail(email).isPresent();
	}



	public VerifyToken createVerificationToken(Person person) {
		VerifyToken verToken = new VerifyToken(person, UUID.randomUUID().toString());
		tokRepository.save(verToken);
		log.info("Token - ".concat(verToken.getToken()).concat(" was created"));
		return verToken;
	}

	public void handleRegistration(Person person) {
		VerifyToken verificationToken = createVerificationToken(person);
		emailService.sendRegistrationMessage(person,verificationToken.getToken(),getAppUrl());
	}

	@Override
	public VerifyToken getVerifyToken(String verifyToken) {
		return tokRepository.findByToken(verifyToken);
	}

	private String getAppUrl() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
	}
}