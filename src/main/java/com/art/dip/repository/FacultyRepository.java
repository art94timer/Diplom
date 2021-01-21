package com.art.dip.repository;


import com.art.dip.model.Faculty;
import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.art.dip.utility.Constants.PATH_TO_FACULTYDTO;
import static com.art.dip.utility.Constants.PATH_TO_FACULTY_INFO_DTO;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {


	@Query("select new "+ PATH_TO_FACULTYDTO +"(f.id,f.ruName) FROM Faculty f")
	List<FacultyDTO> getAllRuFaculties();

	@Query("select new "+ PATH_TO_FACULTYDTO +"(f.id,f.ruName) FROM Faculty f WHere f.id=:id")
	FacultyDTO getRuFacultyById(Integer id);


	@Query("SELECT  new " + PATH_TO_FACULTYDTO +"(f.id,f.ruName) FROM Faculty f where f.info.expiredDate>=:now")
	List<FacultyDTO> getRuFacultiesIsNotExpired(LocalDateTime now);

	@Query("SELECT new " + PATH_TO_FACULTY_INFO_DTO + "(f.id,f.ruName," +
			"i.capacity,i.averageScore,i.countApplicants,i.updateTime,i.expiredDate) FROM Faculty f JOIN f.info i where f.id=:id")
	FacultyInfoDTO getRuFacultyWithInfoById(Integer id);




	@Query("select new "+ PATH_TO_FACULTYDTO +"(f.id,f.name) FROM Faculty f")
	List<FacultyDTO> getAllEnFaculties();

	@Query("select new "+ PATH_TO_FACULTYDTO +"(f.id,f.name) FROM Faculty f Where f.id=:id")
	FacultyDTO getEnFacultyById(Integer id);

	@Query("SELECT  new " + PATH_TO_FACULTYDTO +"(f.id,f.name) FROM Faculty f where f.info.expiredDate>=:now")
	List<FacultyDTO> getEnFacultiesIsNotExpired(LocalDateTime now);


	@Query("SELECT new " + PATH_TO_FACULTY_INFO_DTO + "(f.id,f.name," +
			"i.capacity,i.averageScore,i.countApplicants,i.updateTime,i.expiredDate) FROM Faculty f JOIN f.info i where f.id=:id")
	FacultyInfoDTO getEnFacultyWithInfoById(Integer id);


	@Query("Select f From Faculty f JOIN FETCH f.subjects where f.id=:id")
	Faculty findByIdFacultyInfo(Integer id);


	@Query("Select f From Faculty f JOIN FETCH f.info i where f.id=:id")
	Faculty findFacultyDetailsById(Integer id);

}
