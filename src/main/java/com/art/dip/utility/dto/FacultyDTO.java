package com.art.dip.utility.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyDTO {

	private Integer id;

	private String name;
	
	private List<SubjectDTO> subjects;

	public FacultyDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
}
