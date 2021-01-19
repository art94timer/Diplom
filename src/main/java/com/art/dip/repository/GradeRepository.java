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

    @Query("SELECT new "+ PATH_TO_VALIDATE_GRADE_DTO+"(s.name,g.fileName,g.mark) " +
            "FROM Grade g JOIN Subject s" +
            " ON g.subject.id=s.id JOIN Applicant a ON g.applicant.id=a.id WHERE a.id=:id")
    List<ValidateGradeDTO> getGradesForApplicant(Integer id);
}
