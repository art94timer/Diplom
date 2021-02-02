package com.art.dip.repository;


import com.art.dip.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    List<Grade> findAllByApplicant_Id(Integer id);
}
