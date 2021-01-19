package com.art.dip.service;


import com.art.dip.model.Person;
import com.art.dip.model.VerifyToken;
import com.art.dip.repository.PersonRepository;
import com.art.dip.repository.VerifyTokenRepository;
import com.art.dip.service.interfaces.PersonService;
import com.art.dip.utility.converter.PersonConverter;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.exception.PersonAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class PersonServiceImpl implements PersonService {

	private final CurrentPersonInfoService currentPersonInfoService;

	private final PersonRepository repository;
	
	private final VerifyTokenRepository tokRepository;
	
	private final MessageSource messages;

	private final PersonConverter converter;

	
	@Autowired
	public PersonServiceImpl(CurrentPersonInfoService currentPersonInfoService,
							 PersonRepository repository, VerifyTokenRepository tokRepository,
							 MessageSource messages, PersonConverter converter) {
		this.currentPersonInfoService = currentPersonInfoService;
		this.repository = repository;
		this.tokRepository = tokRepository;
		this.messages = messages;
		this.converter = converter;

	}

	public Optional<Person> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional
	public Person registerNewPersonAccount(PersonDTO person) throws PersonAlreadyExistException  {
		if (emailExist(person.getEmail())) {
			String message = messages.getMessage("message.emailExist", new String[]{person.getEmail()},
					currentPersonInfoService.getCurrentLoggedPersonLocale());

			throw new PersonAlreadyExistException(message,person);
		}
		return repository.save(converter.toEntity(person));
	}

	private boolean emailExist(String email) {
		return repository.findByEmail(email).isPresent();
	}

	@Override
	public Person getPerson(String verifyToken) {
		
		return tokRepository.findByToken(verifyToken).getPerson();
	}

	@Override
	public void saveRegisteredPerson(Person person) {
		repository.save(person);
	}

	@Override
	public void createVerificationToken(Person person, String token) {
		VerifyToken verToken = new VerifyToken(person,token);
		tokRepository.save(verToken);
		
	}

	@Override
	public VerifyToken getVerifyToken(String verifyToken) {
		return tokRepository.findByToken(verifyToken);
	}
}
