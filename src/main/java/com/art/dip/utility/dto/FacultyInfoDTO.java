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

	private boolean isAvailable;

}
