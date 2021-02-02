package com.art.dip.repository;


import com.art.dip.model.Applicant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {


    @Query("SELECT a FROM  Applicant a  join fetch a.person " +
            "where a.faculty.id=:facultyId and a.isAccepted=false")
    List<Applicant> getApplicantsForValidating(Integer facultyId, Pageable pageable);

    @Query("SELECT a FROM  Applicant a  join fetch a.person " +
            "where a.isAccepted=false")
    List<Applicant> getAllApplicantsForValidating(Pageable pageable);

    @Query("SELECT a FROM  Applicant a JOIN FETCH a.person where a.person.email=:email")
     Applicant getApplicantForValidatingByEmail(String email);


    @Modifying
    @Query("update Applicant a set a.isAccepted=true where a.id=:id")
    void validateApplicant(Integer id);

    Applicant findByPerson_Id(Integer id);

    List<Applicant> getApplicantsByFaculty_Id(Integer id);

    @Query("SELECT a from Applicant a JOIN FETCH a.faculty where a.person.id=:id")
    Applicant getApplicantWithFacultyById(Integer id);


    @Query("select a FROM Applicant a JOIN FETCH a.person WHERE a.isAccepted=true and a.faculty.id=:id" +
            " ORDER BY a.score desc, a.registrationTime asc")
    List<Applicant> getAllApplicantWithPersonForAcceptedOrNot(@Param("id") Integer facultyId);


}
