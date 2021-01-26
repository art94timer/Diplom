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

	@Query("SELECT f FROM Faculty f join fetch f.subjects where f.id=:id")
	Faculty findFacultyByIdWithSubject(Integer id);


	@Query("SELECT f FROM Faculty f where f.info.expiredDate>=:now")
	List<Faculty> findAllFacultiesNotExpired(LocalDateTime now);


	@Query("select f From Faculty f JOIN fetch f.info where f.id=:id")
	Faculty findFacultyByIdWithInfo(Integer id);

	List<Faculty> findAllByInfo_IsAvailable(boolean expired);

	@Query("SELECT f FROM Faculty f JOIN FETCH f.info")
	List<Faculty> findAllWithInfo();
}
