package com.art.dip.repository;

import com.art.dip.model.Person;
import com.art.dip.model.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VerifyTokenRepository extends JpaRepository<VerifyToken, Integer>{
	
	VerifyToken findByToken(String token);


	@Modifying
	@Query("DELETE  From VerifyToken t where t.expireDate <= :now")
	void cleanUp(LocalDate now);

	@Query("Select p FROM VerifyToken t JOIN Person p on p.id=t.person.id  where t.expireDate<=:now and p.enabled=false")
	List<Person> getExpiredPersons(LocalDate now);
}
