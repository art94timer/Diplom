package com.art.dip.repository;

import com.art.dip.model.FacultyInfo;
import com.art.dip.utility.dto.UpdateFacultyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FacultyInfoRepository extends JpaRepository<FacultyInfo, Integer> {


    @Query("SELECT  new com.art.dip.utility.dto.UpdateFacultyDTO(Count(a.id),Avg(a.score)) "
            + "FROM Applicant a JOIN a.faculty f"
            + " Where f.id=:id ")
    UpdateFacultyDTO updateFacultyInfo(Integer id);

    @Query("select distinct (f.expiredDate <=:date) FROM FacultyInfo f  where f.id=:id")
    boolean isFacultyExpired(LocalDateTime date, Integer id);
}
