package com.art.dip.utility.exception;


import com.art.dip.utility.dto.PersonDTO;

public class PersonAlreadyExistException extends RuntimeException {

	private final PersonDTO person;

	public PersonAlreadyExistException(String message, PersonDTO person) {
		super(message);
		this.person = person;
	}


	public PersonDTO getPerson() {
		return person;
	}
}
