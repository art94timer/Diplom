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


    @Query("SELECT a FROM  Applicant a  join fetch a.person " +
            "where a.faculty.id=:facultyId and a.isAccepted=false")
    List<Applicant> getApplicantsForValidating(Integer facultyId, Pageable pageable);

    @Query("SELECT a FROM  Applicant a JOIN FETCH a.person where a.person.email=:email")
     Applicant getApplicantForValidatingByEmail(String email);


    @Modifying
    @Query("update Applicant a set a.isAccepted=true where a.id=:id")
    void validateApplicant(Integer id);

    Applicant findByPerson_Id(Integer id);



}
