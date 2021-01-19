package com.art.dip.schedule;

import com.art.dip.repository.VerifyTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class CleanUpVerifyTokenTask {


    private final VerifyTokenRepository repository;

    @Autowired
    public CleanUpVerifyTokenTask(VerifyTokenRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    @Transactional
    public void cleanUp() {
        repository.cleanUp(LocalDate.now());
    }
}
