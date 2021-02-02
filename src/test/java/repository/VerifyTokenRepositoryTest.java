package repository;


import com.art.dip.model.Person;
import com.art.dip.model.VerifyToken;
import com.art.dip.repository.PersonRepository;
import com.art.dip.repository.VerifyTokenRepository;
import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = TestConfig.class)
public class VerifyTokenRepositoryTest {

    private final VerifyTokenRepository repository;

    private final PersonRepository personRepository;

    @Autowired
    public VerifyTokenRepositoryTest(VerifyTokenRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }
    @AfterEach
    public void cleanUpAfter() {
        repository.deleteAll();
        personRepository.deleteAll();
    }
    @Test
    public void start() {
        Assertions.assertNotNull(repository);
        Assertions.assertNotNull(personRepository);
    }

    @Test
    public void findByToken() {
       repository.saveAndFlush(new VerifyToken("art"));
        Assertions.assertNotNull(repository.findByToken("art"));
    }

    @Test
    @Transactional
    public void cleanUp() {
        VerifyToken verifyToken = new VerifyToken();
        verifyToken.setToken("art");
        verifyToken.setExpireDate(LocalDate.now().plusDays(1));
        repository.saveAndFlush(verifyToken);
        repository.cleanUp(LocalDate.now());
        Assertions.assertNotNull(repository.findByToken("art"));
    }

    @Test
    public void getExpiredPerson() {
        Person person = new Person();
        person.setEnabled(false);
        personRepository.save(person);
        VerifyToken token = new VerifyToken();
        token.setExpireDate(LocalDate.now().minusDays(1));
        token.setPerson(person);
        repository.save(token);
        List<Person> list = repository.getExpiredPersons(LocalDate.now());
        Assertions.assertFalse(list.isEmpty());
    }
}
