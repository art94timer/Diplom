package com.art.dip.utility.converter;

import com.art.dip.model.Faculty;
import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.dto.SubjectDTO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FacultyConverter {

	private final SubjectConverter subjectConverter;

	@Autowired
	public FacultyConverter(SubjectConverter subjectConverter) {
		this.subjectConverter = subjectConverter;
	}

	public FacultyDTO toFacultyDTO(Faculty faculty) {
		FacultyDTO facultyDTO = new FacultyDTO();
		facultyDTO.setId(faculty.getId());
		return facultyDTO;
	}

	public FacultyDTO toRuFacultyDTO(Faculty faculty) {
		FacultyDTO facultyDTO = toFacultyDTO(faculty);
		if (faculty.getSubjects() != null && Hibernate.isInitialized(faculty.getSubjects())) {
			List<SubjectDTO> subjects = faculty.getSubjects().stream().map(subjectConverter::toRuSubjectDTO).collect(Collectors.toList());
			facultyDTO.setSubjects(subjects);
		}
		facultyDTO.setName(faculty.getRuName());
		return facultyDTO;
	}
	public List<FacultyDTO> toRuFacultyDTO(List<Faculty> faculties) {
		return faculties.stream().map(this::toRuFacultyDTO).collect(Collectors.toList());
	}
	public FacultyDTO toEnFacultyDTO(Faculty faculty) {
		FacultyDTO facultyDTO = toFacultyDTO(faculty);
		if (faculty.getSubjects() != null && Hibernate.isInitialized(faculty.getSubjects()) ) {
			List<SubjectDTO> subjects = faculty.getSubjects().stream().map(subjectConverter::toEnSubjectDTO).collect(Collectors.toList());
			facultyDTO.setSubjects(subjects);
		}
		facultyDTO.setName(faculty.getName());
		return facultyDTO;
	}
	public List<FacultyDTO> toEnFacultyDTO(List<Faculty> faculties) {
		return faculties.stream().map(this::toEnFacultyDTO).collect(Collectors.toList());
	}

	public Faculty toEntity(FacultyDTO facultyDTO) {
		Faculty faculty = new Faculty();
		faculty.setId(facultyDTO.getId());
		if (facultyDTO.getSubjects() != null) {
			faculty.setSubjects(subjectConverter.toEntity(facultyDTO.getSubjects()));
		}
		return faculty;
	}
	public List<Faculty> toEntity(List<FacultyDTO> faculties) {
		return faculties.stream().map(this::toEntity).collect(Collectors.toList());
	}

}
