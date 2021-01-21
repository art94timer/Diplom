package com.art.dip.repository;


import com.art.dip.model.Subject;
import com.art.dip.utility.dto.SubjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.art.dip.utility.Constants.PATH_TO_SUBJECTDTO;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{


	@Query("select new " + (PATH_TO_SUBJECTDTO) + " (s.id,s.ruName) FROM Subject s join s.faculties f  where f.id=:id" )
	List<SubjectDTO> findRuSubjectsByFacultyId(Integer id);

	@Query("select new " + (PATH_TO_SUBJECTDTO) + " (s.id,s.name) FROM Subject s join s.faculties f where f.id=:id" )
	List<SubjectDTO> findEnSubjectsByFacultyId(Integer id);

 
}
