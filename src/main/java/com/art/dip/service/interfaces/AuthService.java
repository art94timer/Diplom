package com.art.dip.service.interfaces;

import com.art.dip.model.Person;
import com.art.dip.model.VerifyToken;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.exception.PersonAlreadyExistException;

import java.util.Optional;

public interface AuthService {

	void registerNewPersonAccount(PersonDTO personDto) throws PersonAlreadyExistException;

	VerifyToken getVerifyToken(String VerifyToken);

	Optional<Person> findByEmail(String email);

	String tokenIsExpiredMessage();

	void verifyPerson(VerifyToken verificationToken);

	String getVerifyYourEmailMessage();
}
