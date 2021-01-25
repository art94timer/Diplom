package com.art.dip.utility.converter;


import com.art.dip.model.Credential;
import com.art.dip.model.Person;
import com.art.dip.utility.dto.PersonDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;



@Component
public class PersonConverter {


    public Person toEntity(PersonDTO personDTO) {
    	Person person = new Person();
    	person.setEmail(personDTO.getEmail());
    	person.setCredential(new Credential(personDTO.getPassword()));
    	person.setBirthdate(LocalDate.parse(personDTO.getBirthdate()));
    	person.setFirstName(personDTO.getFirstName());
    	person.setLastName(personDTO.getLastName());
    	person.setLocale(personDTO.getLocale());
    	return person;
    }
    
}
