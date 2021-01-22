package com.art.dip.schedule;

import com.art.dip.model.Person;
import com.art.dip.repository.PersonRepository;
import com.art.dip.repository.VerifyTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class CleanUpVerifyTokenTask {


    private final VerifyTokenRepository repository;

    private final PersonRepository personRepository;

    @Autowired
    public CleanUpVerifyTokenTask(VerifyTokenRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    @Transactional
    public void cleanUp()
    {
        LocalDate today = LocalDate.now();
        List<Person> expiredPersons = repository.getExpiredPersons(today);
        repository.cleanUp(today);
        personRepository.deleteAll(expiredPersons);
    }
}
