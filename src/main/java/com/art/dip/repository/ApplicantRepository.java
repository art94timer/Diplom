package com.art.dip.repository;


import com.art.dip.model.Applicant;
import com.art.dip.model.Certificate;
import com.art.dip.utility.dto.ValidateApplicantDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.art.dip.utility.Constants.PATH_TO_VALIDATE_APPLICANT_DTO;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {


    @Query("SELECT new " + PATH_TO_VALIDATE_APPLICANT_DTO + "(a.id, concat(p.firstName,' ',p.lastName), p.email" +
            ",a.certificate) FROM  Person p JOIN Applicant a  ON a.person.id=p.id " +
            "where a.faculty.id=:facultyId")
    List<ValidateApplicantDTO> getApplicantForValidating(Integer facultyId, Pageable pageable);

    @Modifying
    @Query("update Applicant a set a.isAccepted=true where a.id=:id")
    void validateApplicant(Integer id);

    Applicant findByPerson_Id(Integer id);

    @Query("Select a.certificate FROM Applicant a where a.id=:id")
    Certificate getCertificateByApplicantId(Integer id);


}
