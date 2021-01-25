package com.art.dip.repository;

import com.art.dip.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CertificateRepository extends JpaRepository<Certificate,Integer> {

    @Query("Select a.certificate FROM Applicant a where a.id=:id")
    Certificate getCertificateByApplicantId(Integer id);

}
