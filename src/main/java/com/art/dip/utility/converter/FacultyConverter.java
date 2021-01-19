package com.art.dip.utility.converter;

import com.art.dip.model.Faculty;
import com.art.dip.utility.dto.FacultyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacultyConverter {

	private final ModelMapper mapper;

	@Autowired
	public FacultyConverter(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public FacultyDTO toDTO(Faculty faculty) {
		return mapper.map(faculty, FacultyDTO.class);

	}

	public Faculty toEntity(FacultyDTO facultyDTO) {
		return mapper.map(facultyDTO,Faculty.class);
	}

}
