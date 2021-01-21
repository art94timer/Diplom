package com.art.dip.service;

import com.art.dip.model.Faculty;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.SubjectRepository;
import com.art.dip.utility.converter.FacultyConverter;
import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViewServiceImpl {

	private final FacultyRepository facRepository;

	private final CurrentPersonInfoService currentPersonInfoService;

	private final SubjectRepository subjectRepository;

	@Autowired
	public ViewServiceImpl(FacultyRepository facRepository,
						   CurrentPersonInfoService currentPersonInfoService,
						   SubjectRepository subjectRepository) {
		this.facRepository = facRepository;
		this.currentPersonInfoService = currentPersonInfoService;
		this.subjectRepository = subjectRepository;
	}



	public List<FacultyDTO> getAllFaculties() {
		LocalDateTime now = LocalDateTime.now();
		if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
			return facRepository.getRuFacultiesIsNotExpired(now);
		} else {
			return facRepository.getEnFacultiesIsNotExpired(now);
		}
	}

	public FacultyInfoDTO getFacultyInfo(Integer id) {
		if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
			FacultyInfoDTO ruFaculty = facRepository.getRuFacultyWithInfoById(id);
			ruFaculty.setSubjects(subjectRepository.findRuSubjectsByFacultyId(id));
			return ruFaculty;
		} else {
			FacultyInfoDTO enFaculty = facRepository.getEnFacultyWithInfoById(id);
			enFaculty.setSubjects(subjectRepository.findEnSubjectsByFacultyId(id));
			return enFaculty;
		}
	}

}
