package com.art.dip.repository;

import com.art.dip.model.InvalidApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidApplicantRepository extends JpaRepository<InvalidApplicant,Integer> {


}
