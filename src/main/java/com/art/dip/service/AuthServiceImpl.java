package com.art.dip.service;


import com.art.dip.model.Person;
import com.art.dip.model.Role;
import com.art.dip.model.VerifyToken;
import com.art.dip.repository.PersonRepository;
import com.art.dip.repository.VerifyTokenRepository;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.service.interfaces.PersonService;
import com.art.dip.utility.converter.PersonConverter;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.exception.PersonAlreadyExistException;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthServiceImpl implements PersonService {

	private final PersonRepository repository;

	private final VerifyTokenRepository tokRepository;

	private final MessageSourceService mesService;

	private final PersonConverter converter;

	private final EmailService emailService;

	private final CurrentPersonInfoService currentPersonInfoService;

	@Autowired
	public AuthServiceImpl(
			PersonRepository repository, VerifyTokenRepository tokRepository,
			MessageSourceService mesService, PersonConverter converter, EmailService emailService, CurrentPersonInfoService currentPersonInfoService) {
		this.repository = repository;
		this.tokRepository = tokRepository;
		this.mesService = mesService;
		this.converter = converter;
		this.emailService = emailService;
		this.currentPersonInfoService = currentPersonInfoService;
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
	}

	@Override
	public String getVerifyYourEmailMessage() {
		return mesService.getVerifyYourEmailMessage();
	}


	@Override
	@Transactional
	public Person registerNewPersonAccount(PersonDTO person) throws PersonAlreadyExistException {
		Locale locale = currentPersonInfoService.getCurrentLoggedPersonLocale();
		if (emailExist(person.getEmail())) {
			String message = mesService.getEmailIsExistMessage(locale,person.getEmail());
			throw new PersonAlreadyExistException(message, person);
		}
		Person converted = converter.toEntity(person);
		converted.setRole(Role.USER);
		converted.setLocale(locale);
		Person saved = repository.save(converted);
		handleRegistration(saved);
		return saved;

	}

	private boolean emailExist(String email) {
		return repository.findByEmail(email).isPresent();
	}

	@Override
	public Person getPerson(String verifyToken) {
		return tokRepository.findByToken(verifyToken).getPerson();
	}

	@Override
	public VerifyToken createVerificationToken(Person person) {
		VerifyToken verToken = new VerifyToken(person, UUID.randomUUID().toString());
		tokRepository.save(verToken);
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