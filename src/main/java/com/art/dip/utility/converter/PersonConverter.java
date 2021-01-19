package com.art.dip.utility.converter;


import com.art.dip.model.Credential;
import com.art.dip.model.Person;
import com.art.dip.model.Role;
import com.art.dip.utility.dto.PersonDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class PersonConverter {
	
	private final ModelMapper mapper;
	
	private final Converter<String, LocalDate> toLocalDate;
    
    public PersonConverter(ModelMapper mapper, Converter<String, LocalDate> toLocalDate) {
		this.mapper = mapper;
		this.toLocalDate = toLocalDate;
	}

	@PostConstruct
    public void setup() {
	 	mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    	mapper.typeMap(PersonDTO.class, Person.class).addMappings(m -> {
			m.map(src -> new Credential(src.getPassword()), Person::setCredential);
			m.map(src -> Role.USER, Person::setRole);
			m.using(toLocalDate).map(PersonDTO::getBirthdate, Person::setBirthdate);

		});
    }

    public Person toEntity(PersonDTO personDTO) {
    	return mapper.map(personDTO,Person.class);
    }
    
}
