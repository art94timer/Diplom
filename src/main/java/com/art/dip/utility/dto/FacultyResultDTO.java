package com.art.dip.utility.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResultDTO {

	private Integer id;
	
	private String name;
	
	private Integer capacity;
	
	private Double averageScore;
	
	private Integer countApplicants;

	private LocalDateTime updateTime;
	
	private LocalDateTime expiredTime;
	
	
}
