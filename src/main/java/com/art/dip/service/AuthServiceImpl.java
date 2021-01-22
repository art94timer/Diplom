package com.art.dip.service;


import com.art.dip.model.Person;
import com.art.dip.model.VerifyToken;
import com.art.dip.repository.PersonRepository;
import com.art.dip.repository.VerifyTokenRepository;
import com.art.dip.service.interfaces.PersonService;
import com.art.dip.utility.converter.PersonConverter;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;
import com.art.dip.utility.exception.PersonAlreadyExistException;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class AuthServiceImpl implements PersonService {

	private final CurrentPersonInfoService currentPersonInfoService;

	private final PersonRepository repository;

	private final VerifyTokenRepository tokRepository;

	private final MessageSourceService mesService;

	private final PersonConverter converter;

	private final ApplicationEventPublisher eventPublisher;

	@Autowired
	public AuthServiceImpl(CurrentPersonInfoService currentPersonInfoService,
						   PersonRepository repository, VerifyTokenRepository tokRepository,
						   MessageSourceService mesService, PersonConverter converter, ApplicationEventPublisher eventPublisher) {
		this.currentPersonInfoService = currentPersonInfoService;
		this.repository = repository;
		this.tokRepository = tokRepository;
		this.mesService = mesService;
		this.converter = converter;

		this.eventPublisher = eventPublisher;
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
	@Transactional
	public Person registerNewPersonAccount(PersonDTO person) throws PersonAlreadyExistException {

		if (emailExist(person.getEmail())) {
			String message = mesService.getEmailIsExistMessage(person.getEmail());
			throw new PersonAlreadyExistException(message, person);
		}

		Person saved = repository.save(converter.toEntity(person));
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(getAppUrl(),
						currentPersonInfoService.getCurrentLoggedPersonLocale(),
						saved));
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
	public void createVerificationToken(Person person, String token) {
		VerifyToken verToken = new VerifyToken(person, token);
		tokRepository.save(verToken);

	}

	@Override
	public VerifyToken getVerifyToken(String verifyToken) {
		return tokRepository.findByToken(verifyToken);
	}


	private String getAppUrl() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
	}
}