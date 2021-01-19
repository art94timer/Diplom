package com.art.dip.utility.converter;


import com.art.dip.model.Grade;
import com.art.dip.service.ApplicantPhotoService;
import com.art.dip.utility.dto.GradeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GradeConverter {
	
	private final ModelMapper mapper;
	
	private final ApplicantPhotoService photoService;
	
	


	@Autowired
	public GradeConverter(ModelMapper mapper, ApplicantPhotoService photoService) {
		this.mapper = mapper;
		this.photoService = photoService;
	}

	public Grade toEntity(GradeDTO dto) {
		Grade grade = mapper.map(dto, Grade.class);
		String fileName = photoService.uploadPhoto(dto.getFile());
		grade.setFileName(fileName);
		return grade;
	}
	
	public List<Grade> toEntityList(List<GradeDTO> gradesDTO) {
		return gradesDTO.stream().map(this::toEntity).collect(Collectors.toList());
	}
}
