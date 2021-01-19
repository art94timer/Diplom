package com.art.dip.repository;


import com.art.dip.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubjectRepository extends JpaRepository<Subject, Integer>{


	List<Subject> findByFaculties_Id(Integer id);
 
}
