package repository;


import com.art.dip.model.Person;
import com.art.dip.model.VerifyToken;
import com.art.dip.repository.PersonRepository;
import com.art.dip.repository.VerifyTokenRepository;
import config.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class VerifyTokenRepositoryTest {

    private final VerifyTokenRepository repository;

    private final PersonRepository personRepository;

    @Autowired
    public VerifyTokenRepositoryTest(VerifyTokenRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    @Test
    public void start() {
        Assertions.assertNotNull(repository);
        Assertions.assertNotNull(personRepository);
    }

    @Test
    public void findByToken() {
        VerifyToken tok = repository.saveAndFlush(new VerifyToken("art"));
        Assertions.assertNotNull(repository.findByToken("art"));
    }

    @Test
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
