package com.art.dip.utility.converter;


import com.art.dip.model.Grade;
import com.art.dip.service.ApplicantPhotoService;
import com.art.dip.utility.BuildPathAmazonService;
import com.art.dip.utility.dto.GradeDTO;
import com.art.dip.utility.dto.SubjectDTO;
import com.art.dip.utility.dto.ValidateGradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GradeConverter {

	private final BuildPathAmazonService buildPathAmazonService;

	private final ApplicantPhotoService photoService;

	@Autowired
	public GradeConverter(BuildPathAmazonService buildPathAmazonService, ApplicantPhotoService photoService) {
		this.buildPathAmazonService = buildPathAmazonService;
		this.photoService = photoService;
	}


	public ValidateGradeDTO toRuValidateGradeDTO(Grade grade) {
		ValidateGradeDTO validateGradeDTO = toValidateGradeDTO(grade);
		validateGradeDTO.setSubject(new SubjectDTO(grade.getSubject().getId(),grade.getSubject().getRuName()));
		return validateGradeDTO;
	}

	public List<ValidateGradeDTO> toRuValidateGradeDTO(List<Grade> grades) {
		return grades.stream().map(this::toRuValidateGradeDTO).collect(Collectors.toList());
	}

	public ValidateGradeDTO toEnValidateGradeDTO(Grade grade) {
		ValidateGradeDTO validateGradeDTO = toValidateGradeDTO(grade);
		validateGradeDTO.setSubject(new SubjectDTO(grade.getSubject().getId(),grade.getSubject().getName()));
		return validateGradeDTO;
	}

	public List<ValidateGradeDTO> toEnValidateGradeDTO(List<Grade> grades) {
		return grades.stream().map(this::toEnValidateGradeDTO).collect(Collectors.toList());
	}

	private ValidateGradeDTO toValidateGradeDTO(Grade grade) {
		ValidateGradeDTO validateGradeDTO = new ValidateGradeDTO();
		validateGradeDTO.setFileName(buildPathAmazonService.buildPath(grade.getFileName()));
		validateGradeDTO.setMark(grade.getMark());
		return validateGradeDTO;
	}


	public Grade toEntity(GradeDTO dto) {
		Grade grade = new Grade();
		grade.setSubject(dto.getSubject());
		grade.setMark(dto.getMark());
		String fileName = photoService.uploadPhoto(dto.getFile());
		grade.setFileName(fileName);
		return grade;
	}
	
	public List<Grade> toEntity(List<GradeDTO> gradesDTO) {
		return gradesDTO.stream().map(this::toEntity).collect(Collectors.toList());
	}
}
