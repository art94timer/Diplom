package com.art.dip.repository;


import com.art.dip.model.Faculty;
import com.art.dip.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{

	@Query("select s from Subject s join fetch s.faculties where s.id=:id")
		Subject getSubjectsWithFaculties(Integer id);


	List<Subject> findAllByFacultiesContaining(Faculty faculty);

	@Query("SELECT s FROM Subject s JOIN fetch s.faculties where s.id=:id")
	Subject getSubjectByIdWithFaculties(Integer id);


}
