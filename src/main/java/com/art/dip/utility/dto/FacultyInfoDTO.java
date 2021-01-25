package com.art.dip.utility.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyInfoDTO {

	private Integer id;
	
	private String name;
	
	private Integer capacity;
	
	private Double averageScore;
	
	private Integer countApplicants;

	private LocalDateTime updateTime;
	
	private LocalDateTime expiredDate;

	private List<SubjectDTO> subjects;

	private boolean isExpired;

	public FacultyInfoDTO(Integer id, String name,
						  Integer capacity, Double averageScore,
						  Integer countApplicants, LocalDateTime updateTime,
						  LocalDateTime expiredDate) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.averageScore = averageScore;
		this.countApplicants = countApplicants;
		this.updateTime = updateTime;
		this.expiredDate = expiredDate;
	}
}
