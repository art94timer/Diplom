package com.art.dip.service.interfaces;

import com.art.dip.model.Faculty;

import java.util.List;


public interface FacultyService {
	
	List<Faculty> getAll();

	Faculty findByIdWithSpecialties(Integer facultyId);

}
