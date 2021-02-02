package com.art.dip.repository;


import com.art.dip.model.Faculty;
import com.art.dip.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{


	List<Subject> findAllByFacultiesContaining(Faculty faculty);



}
