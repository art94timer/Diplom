package com.art.dip.repository;


import com.art.dip.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

	@Query("SELECT f FROM Faculty f LEFT JOIN FETCH f.subjects where f.id=:id")
	Faculty findFacultyByIdWithSubject(Integer id);



	@Query("SELECT f FROM Faculty f where f.info.expiredDate>=:now")
	List<Faculty> findAllFacultiesNotExpired(LocalDateTime now);


	@Query("select f From Faculty f JOIN fetch f.info where f.id=:id")
	Faculty findFacultyByIdWithInfo(Integer id);

	List<Faculty> findAllByInfo_IsAvailable(boolean expired);

	@Query("SELECT f FROM Faculty f JOIN FETCH f.info")
	List<Faculty> findAllWithInfo();
}
