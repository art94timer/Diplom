package com.art.dip.repository;


import com.art.dip.model.Faculty;
import com.art.dip.utility.dto.FacultyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {


	@Query("Select f From Faculty f JOIN FETCH f.subjects where f.id=:id")
	Faculty findByIdFacultyInfo(Integer id);

	
	@Query("Select f From Faculty f JOIN FETCH f.subjects Where f.id=:id")
	Faculty findByIdWithSubjects(Integer id);

	
	@Query("Select f From Faculty f JOIN FETCH f.info i where f.id=:id")
	Faculty findFacultyDetailsById(Integer id);
	
	@Query("select new com.art.dip.utility.dto.FacultyDTO(f.id,f.name) from  Faculty f")
	 List<FacultyDTO> getAllFacultiesOnlyNameAndId();

}
