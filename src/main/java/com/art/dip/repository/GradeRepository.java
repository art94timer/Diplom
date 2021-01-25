package com.art.dip.repository;


import com.art.dip.model.Grade;
import com.art.dip.utility.dto.ValidateGradeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.art.dip.utility.Constants.PATH_TO_VALIDATE_GRADE_DTO;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    List<Grade> findAllByApplicant_Id(Integer id);
}
