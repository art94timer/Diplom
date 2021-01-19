package com.art.dip.service;

import com.art.dip.model.Faculty;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.utility.converter.FacultyConverter;
import com.art.dip.utility.dto.FacultyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewServiceImpl {



	private final FacultyRepository facRepository;

	private final FacultyConverter facConverter;

	private final CurrentPersonInfoService currentPersonInfoService;

	@Autowired
	public ViewServiceImpl(FacultyRepository facRepository,
						   FacultyConverter facConverter,
						   CurrentPersonInfoService currentPersonInfoService) {
		this.facRepository = facRepository;
		this.facConverter = facConverter;
		this.currentPersonInfoService = currentPersonInfoService;
	}



	public List<FacultyDTO> getAllFaculties() {
		return facRepository.getAllFacultiesOnlyNameAndId();
	}

	public Faculty getFacultyInfo(Integer id) {


		return facRepository.findByIdFacultyInfo(id);

	}

	public FacultyDTO getFacultyDTOWithDetailsById(Integer id) {

		Faculty faculty = facRepository.findFacultyDetailsById(id);
		return facConverter.toDTO(faculty);

	}



}
