package com.art.dip.repository;

import com.art.dip.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{

	@Modifying
	@Query("UPDATE  Person p set p.locale=:locale where p.id=:id")
	void changeLocale(Locale locale, Integer id);
	
	Optional<Person> findByEmail(String email);
}
